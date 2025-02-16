package com.coindex.basic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import java.lang.ref.WeakReference
import java.lang.reflect.ParameterizedType
import java.util.concurrent.ConcurrentHashMap

abstract class BindingFragment<T : ViewBinding> : BasicFragment() {

    private var _binding: T? = null
    protected val binding: T get() = _binding!!

    private companion object {
        // 使用 WeakReference 缓存反射结果
        private val bindingCache = ConcurrentHashMap<String, WeakReference<Class<out ViewBinding>>>()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = createBinding(inflater, container)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @Suppress("UNCHECKED_CAST")
    private fun createBinding(inflater: LayoutInflater, container: ViewGroup?): T {
        val type = (this::class.java.genericSuperclass as ParameterizedType).actualTypeArguments[0]
        val bindingClass = getBindingClass(type.typeName)
        try {
            // 调用 ViewBinding 的 inflate 方法
            val inflateMethod = bindingClass.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
            return inflateMethod.invoke(null, inflater, container, false) as T
        } catch (e: Exception) {
            throw RuntimeException("Failed to create binding instance for ${bindingClass.name}", e)
        }
    }

    private fun getBindingClass(bindingClassName: String): Class<out ViewBinding> {
        return bindingCache[bindingClassName]?.get() ?: run {
            val clazz = Class.forName(bindingClassName) as Class<out ViewBinding>
            require(ViewBinding::class.java.isAssignableFrom(clazz)) {
                "Generic type $bindingClassName must be a subclass of ViewBinding"
            }
            bindingCache[bindingClassName] = WeakReference(clazz)
            clazz
        }
    }
}