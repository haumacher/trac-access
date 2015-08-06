/*
 * Copyright (c) 2005-2015, Alec Thomas et al.
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * The views and conclusions contained in the software and documentation are those
 * of the authors and should not be interpreted as representing official policies,
 * either expressed or implied, of the FreeBSD Project.
 */
package org.lustin.trac.xmlprc;

/**
 *
 * @author lustin
 */
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.common.TypeConverter;
import org.apache.xmlrpc.common.TypeConverterFactory;
import org.apache.xmlrpc.common.TypeConverterFactoryImpl;

public class TrackerDynamicProxy
{
    
    private final XmlRpcClient         client;
    private final TypeConverterFactory typeConverterFactory;
    private boolean                    objectMethodLocal;
    
    /**
     * Creates a new instance.
     * 
     * @param client
     *            A fully configured XML-RPC client, which is used internally to
     *            perform XML-RPC calls.
     * @param typeConverterFactory
     *            Creates instances of {@link TypeConverterFactory}, which are
     *            used to transform the result object in its target
     *            representation.
     */
    public TrackerDynamicProxy( XmlRpcClient client,
            TypeConverterFactory typeConverterFactory )
    {
        this.typeConverterFactory = typeConverterFactory;
        this.client = client;
    }
    
    /**
     * Creates a new instance. Shortcut for
     * 
     * <pre>
     * new ClientFactory( pClient, new TypeConverterFactoryImpl() );
     * </pre>
     * 
     * @param client
     *            A fully configured XML-RPC client, which is used internally to
     *            perform XML-RPC calls.
     * @see TypeConverterFactoryImpl
     */
    public TrackerDynamicProxy( XmlRpcClient client )
    {
        this( client, new TypeConverterFactoryImpl() );
    }
    
    /**
     * Returns the factories client.
     */
    public XmlRpcClient getClient()
    {
        return client;
    }
    
    /**
     * Returns, whether a method declared by the {@link Object Object class} is
     * performed by the local object, rather than by the server. Defaults to
     * true.
     */
    public boolean isObjectMethodLocal()
    {
        return objectMethodLocal;
    }
    
    /**
     * Sets, whether a method declared by the {@link Object Object class} is
     * performed by the local object, rather than by the server. Defaults to
     * true.
     */
    public void setObjectMethodLocal( boolean objectMethodLocal )
    {
        this.objectMethodLocal = objectMethodLocal;
    }
    
    /**
     * Creates an object, which is implementing the given interface. The objects
     * methods are internally calling an XML-RPC server by using the factories
     * client.
     */
    public Object newInstance( Class clazz )
    {
        return newInstance( Thread.currentThread().getContextClassLoader(),
                clazz );
    }
    
    
    /**
     * Creates an object, which is implementing the given interface. The objects
     * methods are internally calling an XML-RPC server by using the factories
     * client.
     */
    public Object newInstance( ClassLoader classLoader, final Class clazz )
    {
        return Proxy.newProxyInstance( classLoader, new Class[] { clazz },
                new InvocationHandler() {
                    public Object invoke( Object proxy, Method method,
                            Object[] args ) throws Throwable
                    {
                        if ( isObjectMethodLocal()
                                && method.getDeclaringClass().equals(
                                        Object.class ) ) { return method.invoke( proxy, args ); }
                    
                        String _classname = clazz.getName().replaceFirst(clazz.getPackage().getName()+".", "").toLowerCase();
                        
                        _classname = _classname.replace("$", "."); //dirty hack TODO check
                        
                        String methodName = _classname
                                + "." + method.getName();
                        Object result = client.execute( methodName, args );
                        TypeConverter typeConverter = typeConverterFactory
                                .getTypeConverter( method.getReturnType() );
                        return typeConverter.convert( result );
                    }
                } );
    }
}
