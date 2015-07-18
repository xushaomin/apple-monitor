/**
* Copyright (c) 2004-2005 jManage.org
*
* This is a free software; you can redistribute it and/or
* modify it under the terms of the license at
* http://www.jmanage.org.
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.appleframework.jmx.core.management;

import java.lang.reflect.*;
import java.io.Serializable;

/**
 * This is generic proxy object to handle incompatible JMX interfaces.
 * This proxy object wraps the incompatible object, and uses reflection
 * to invoke the same operation on the wrapped object.
 * <p>
 * This prevents the ClassCastException because of different classloaders.
 *
 * <p>
 * Date:  Sep 27, 2005
 * @author	Rakesh Kalra
 * @see com.appleframework.jmx.core.config.ClassLoaderRepository
 */
public class JMXInterfaceProxy implements InvocationHandler, Serializable {

	private static final long serialVersionUID = 1L;

	public static Object newProxyInstance(Class<?> clazz, Object object){
        assert clazz.isInstance(object);
        JMXInterfaceProxy proxy = new JMXInterfaceProxy(clazz, object);
        return Proxy.newProxyInstance(JMXInterfaceProxy.class.getClassLoader(), new Class[]{clazz}, proxy);
    }

    private Class<?> clazz;
    private Object wrappedObject;

    private JMXInterfaceProxy(Class<?> clazz, Object wrappedObject){
        this.clazz = clazz;
        this.wrappedObject = wrappedObject;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            Method wrappedMethod = clazz.getMethod(method.getName(), (Class[])method.getParameterTypes());
            return wrappedMethod.invoke(wrappedObject, args);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}
