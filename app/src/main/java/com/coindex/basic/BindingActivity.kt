package com.coindex.basic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType
import java.lang.ref.WeakReference
import java.util.concurrent.ConcurrentHashMap

/**
 * ViewBinding
 */
abstract class BindingActivity<T : ViewBinding> : BasicActivity() {

    private var _binding: T? = null
    protected val binding: T get() = _binding!!

    private companion object {
        // 使用 WeakReference 缓存反射结果
        private val bindingCache = ConcurrentHashMap<String, WeakReference<Class<out ViewBinding>>>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = createBinding(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    @Suppress("UNCHECKED_CAST")
    private fun createBinding(inflater: LayoutInflater): T {
        val type = (this::class.java.genericSuperclass as ParameterizedType).actualTypeArguments[0]
        val bindingClass = bindingCache.getOrPut(type.typeName) {
            WeakReference(Class.forName(type.typeName).also { clazz ->
                require(ViewBinding::class.java.isAssignableFrom(clazz)) {
                    "Generic type must be a subclass of ViewBinding"
                }
            } as Class<out ViewBinding>?)
        }.get()
        require(bindingClass != null) { "Binding class not found" }
        try {
            // 调用 ViewBinding 的 inflate 方法
            val inflateMethod = bindingClass.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
            return inflateMethod.invoke(null, inflater, null, false) as T
        } catch (e: Exception) {
            throw RuntimeException("Failed to create binding instance", e)
        }
    }
}