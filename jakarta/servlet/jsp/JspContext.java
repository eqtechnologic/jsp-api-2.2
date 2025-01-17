/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2008 Sun Microsystems, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License. You can obtain
 * a copy of the License at https://glassfish.dev.java.net/public/CDDL+GPL.html
 * or glassfish/bootstrap/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at glassfish/bootstrap/legal/LICENSE.txt.
 * Sun designates this particular file as subject to the "Classpath" exception
 * as provided by Sun in the GPL Version 2 section of the License file that
 * accompanied this code.  If applicable, add the following below the License
 * Header, with the fields enclosed by brackets [] replaced by your own
 * identifying information: "Portions Copyrighted [year]
 * [name of copyright owner]"
 *
 * Contributor(s):
 *
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 *
 *
 * This file incorporates work covered by the following copyright and
 * permission notice:
 *
 * Copyright 2004 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jakarta.servlet.jsp;

import java.util.Enumeration;

import jakarta.servlet.jsp.el.ExpressionEvaluator;
import jakarta.servlet.jsp.el.VariableResolver;

import jakarta.el.ELContext;

/**
 * <p>
 * <code>JspContext</code> serves as the base class for the 
 * PageContext class and abstracts all information that is not specific
 * to servlets.  This allows for Simple Tag Extensions to be used
 * outside of the context of a request/response Servlet.
 * <p>
 * The JspContext provides a number of facilities to the 
 * page/component author and page implementor, including:
 * <ul>
 * <li>a single API to manage the various scoped namespaces
 * <li>a mechanism to obtain the JspWriter for output
 * <li>a mechanism to expose page directive attributes to the 
 *     scripting environment
 * </ul>
 *
 * <p><B>Methods Intended for Container Generated Code</B>
 * <p>
 * The following methods enable the <B>management of nested</B> JspWriter 
 * streams to implement Tag Extensions: <code>pushBody()</code> and
 * <code>popBody()</code>
 *
 * <p><B>Methods Intended for JSP authors</B>
 * <p>
 * Some methods provide <B>uniform access</B> to the diverse objects
 * representing scopes.
 * The implementation must use the underlying machinery
 * corresponding to that scope, so information can be passed back and
 * forth between the underlying environment (e.g. Servlets) and JSP pages.
 * The methods are:
 * <code>setAttribute()</code>,  <code>getAttribute()</code>,
 * <code>findAttribute()</code>,  <code>removeAttribute()</code>,
 * <code>getAttributesScope()</code> and 
 * <code>getAttributeNamesInScope()</code>.
 * 
 * <p>
 * The following methods provide <B>convenient access</B> to implicit objects:
 * <code>getOut()</code>
 *
 * <p>
 * The following methods provide <B>programmatic access</b> to the 
 * Expression Language evaluator:
 * <code>getExpressionEvaluator()</code>, <code>getVariableResolver()</code>
 *
 * @since JSP 2.0
 */

public abstract class JspContext {

    /**
     * Sole constructor. (For invocation by subclass constructors, 
     * typically implicit.)
     */
    public JspContext() {
    }
    
    /** 
     * Register the name and value specified with page scope semantics.
     * If the value passed in is <code>null</code>, this has the same 
     * effect as calling 
     * <code>removeAttribute( name, PageContext.PAGE_SCOPE )</code>.
     *
     * @param name the name of the attribute to set
     * @param value the value to associate with the name, or null if the
     *     attribute is to be removed from the page scope.
     * @throws NullPointerException if the name is null
     */

    abstract public void setAttribute(String name, Object value);

    /**
     * Register the name and value specified with appropriate 
     * scope semantics.  If the value passed in is <code>null</code>, 
     * this has the same effect as calling
     * <code>removeAttribute( name, scope )</code>.
     * 
     * @param name the name of the attribute to set
     * @param value the object to associate with the name, or null if
     *     the attribute is to be removed from the specified scope.
     * @param scope the scope with which to associate the name/object
     * 
     * @throws NullPointerException if the name is null
     * @throws IllegalArgumentException if the scope is invalid
     * @throws IllegalStateException if the scope is 
     *     PageContext.SESSION_SCOPE but the page that was requested
     *     does not participate in a session or the session has been
     *     invalidated.
     */

    abstract public void setAttribute(String name, Object value, int scope);

    /**
     * Returns the object associated with the name in the page scope or null
     * if not found.
     *
     * @param name the name of the attribute to get
     * @return the object associated with the name in the page scope 
     *     or null if not found.
     * 
     * @throws NullPointerException if the name is null
     */

    abstract public Object getAttribute(String name);

    /**
     * Return the object associated with the name in the specified
     * scope or null if not found.
     *
     * @param name the name of the attribute to set
     * @param scope the scope with which to associate the name/object
     * @return the object associated with the name in the specified
     *     scope or null if not found.
     * 
     * @throws NullPointerException if the name is null
     * @throws IllegalArgumentException if the scope is invalid 
     * @throws IllegalStateException if the scope is 
     *     PageContext.SESSION_SCOPE but the page that was requested
     *     does not participate in a session or the session has been
     *     invalidated.
     */

    abstract public Object getAttribute(String name, int scope);

    /**
     * Searches for the named attribute in page, request, session (if valid),
     * and application scope(s) in order and returns the value associated or
     * null.
     *
     * @param name the name of the attribute to search for
     * @return the value associated or null
     * @throws NullPointerException if the name is null
     */

    abstract public Object findAttribute(String name);

    /**
     * Remove the object reference associated with the given name
     * from all scopes.  Does nothing if there is no such object.
     *
     * @param name The name of the object to remove.
     * @throws NullPointerException if the name is null
     */

    abstract public void removeAttribute(String name);

    /**
     * Remove the object reference associated with the specified name
     * in the given scope.  Does nothing if there is no such object.
     *
     * @param name The name of the object to remove.
     * @param scope The scope where to look.
     * @throws IllegalArgumentException if the scope is invalid
     * @throws IllegalStateException if the scope is 
     *     PageContext.SESSION_SCOPE but the page that was requested
     *     does not participate in a session or the session has been
     *     invalidated.
     * @throws NullPointerException if the name is null
     */

    abstract public void removeAttribute(String name, int scope);

    /**
     * Get the scope where a given attribute is defined.
     *
     * @param name the name of the attribute to return the scope for
     * @return the scope of the object associated with the name specified or 0
     * @throws NullPointerException if the name is null
     */

    abstract public int getAttributesScope(String name);

    /**
     * Enumerate all the attributes in a given scope.
     *
     * @param scope the scope to enumerate all the attributes for
     * @return an enumeration of names (java.lang.String) of all the 
     *     attributes the specified scope
     * @throws IllegalArgumentException if the scope is invalid
     * @throws IllegalStateException if the scope is 
     *     PageContext.SESSION_SCOPE but the page that was requested
     *     does not participate in a session or the session has been
     *     invalidated.
     */

    abstract public Enumeration<String> getAttributeNamesInScope(int scope);

    /**
     * The current value of the out object (a JspWriter).
     *
     * @return the current JspWriter stream being used for client response
     */
    abstract public JspWriter getOut();
    
    /**
     * Provides programmatic access to the ExpressionEvaluator.
     * The JSP Container must return a valid instance of an 
     * ExpressionEvaluator that can parse EL expressions.
     *
     * @deprecated As of JSP 2.1, replaced by
     *     {@link JspApplicationContext#getExpressionFactory}
     * @return A valid instance of an ExpressionEvaluator.
     * @since JSP 2.0
     */
    public abstract ExpressionEvaluator getExpressionEvaluator();
    
    /**
     * Returns an instance of a VariableResolver that provides access to the
     * implicit objects specified in the JSP specification using this JspContext
     * as the context object.
     *
     * @deprecated As of JSP 2.1, replaced by {@link ELContext#getELResolver},
     *     which can be obtained by 
     *     <code>jspContext.getELContext().getELResolver()</code>.
     * @return A valid instance of a VariableResolver.
     * @since JSP 2.0
     */
    public abstract VariableResolver getVariableResolver();
    
    /**
     * Returns the <code>ELContext</code> associated with this 
     * <code>JspContext</code>.
     *
     * <p>The <code>ELContext</code> is created lazily and is reused if 
     * it already exists. There is a new <code>ELContext</code> for each
     * <code>JspContext</code>.</p>
     *
     * <p>The <code>ELContext</code> must contain the <code>ELResolver</code>
     * described in the JSP specification (and in the javadocs for
     * {@link JspApplicationContext#addELResolver}).</p>
     *
     * @return The <code>ELContext</code> associated with this
     *     <code>JspContext</code>.
     * @since JSP 2.1
     */
    public abstract ELContext getELContext();

    /**
     * Return a new JspWriter object that sends output to the
     * provided Writer.  Saves the current "out" JspWriter,
     * and updates the value of the "out" attribute in the
     * page scope attribute namespace of the JspContext.
     * <p>The returned JspWriter must implement all methods and
     * behave as though it were unbuffered.  More specifically:
     * <ul>
     *   <li>clear() must throw an IOException</li>
     *   <li>clearBuffer() does nothing</li>
     *   <li>getBufferSize() always returns 0</li>
     *   <li>getRemaining() always returns 0</li>
     * </ul>
     * </p>
     *
     * @param writer The Writer for the returned JspWriter to send
     *     output to.
     * @return a new JspWriter that writes to the given Writer.
     * @since JSP 2.0
     */
    public JspWriter pushBody( java.io.Writer writer ) {
        return null; // XXX to implement
    }
    
    /**
     * Return the previous JspWriter "out" saved by the matching
     * pushBody(), and update the value of the "out" attribute in
     * the page scope attribute namespace of the JspContext.
     *
     * @return the saved JspWriter.
     */
    public JspWriter popBody() {
        return null; // XXX to implement
    }
}
