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
package jakarta.servlet.jsp.tagext;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.PageContext;

/**
 * A base class for defining new tag handlers implementing Tag.
 *
 * <p> The TagSupport class is a utility class intended to be used as
 * the base class for new tag handlers.  The TagSupport class
 * implements the Tag and IterationTag interfaces and adds additional
 * convenience methods including getter methods for the properties in
 * Tag.  TagSupport has one static method that is included to
 * facilitate coordination among cooperating tags.
 *
 * <p> Many tag handlers will extend TagSupport and only redefine a
 * few methods. 
 */

public class TagSupport implements IterationTag, Serializable {

    /**
     * Find the instance of a given class type that is closest to a given
     * instance.
     * This method uses the getParent method from the Tag
     * interface.
     * This method is used for coordination among cooperating tags.
     *
     * <p>
     * The current version of the specification only provides one formal
     * way of indicating the observable type of a tag handler: its
     * tag handler implementation class, described in the tag-class
     * subelement of the tag element.  This is extended in an
     * informal manner by allowing the tag library author to
     * indicate in the description subelement an observable type.
     * The type should be a subtype of the tag handler implementation
     * class or void.
     * This addititional constraint can be exploited by a
     * specialized container that knows about that specific tag library,
     * as in the case of the JSP standard tag library.
     *
     * <p>
     * When a tag library author provides information on the
     * observable type of a tag handler, client programmatic code
     * should adhere to that constraint.  Specifically, the Class
     * passed to findAncestorWithClass should be a subtype of the
     * observable type.
     * 
     *
     * @param from The instance from where to start looking.
     * @param klass The subclass of Tag or interface to be matched
     * @return the nearest ancestor that implements the interface
     * or is an instance of the class specified
     */

    public static final Tag findAncestorWithClass(Tag from, Class klass) {
	boolean isInterface = false;

	if (from == null ||
	    klass == null ||
	    (!Tag.class.isAssignableFrom(klass) &&
	     !(isInterface = klass.isInterface()))) {
	    return null;
	}

	for (;;) {
	    Tag tag = from.getParent();

	    if (tag == null) {
		return null;
	    }

	    if ((isInterface && klass.isInstance(tag)) ||
	        klass.isAssignableFrom(tag.getClass()))
		return tag;
	    else
		from = tag;
	}
    }

    /**
     * Default constructor, all subclasses are required to define only
     * a public constructor with the same signature, and to call the
     * superclass constructor.
     *
     * This constructor is called by the code generated by the JSP
     * translator.
     */

    public TagSupport() { }

    /**
     * Default processing of the start tag, returning SKIP_BODY.
     *
     * @return SKIP_BODY
     * @throws JspException if an error occurs while processing this tag
     *
     * @see Tag#doStartTag()
     */
 
    public int doStartTag() throws JspException {
        return SKIP_BODY;
    }

    /**
     * Default processing of the end tag returning EVAL_PAGE.
     *
     * @return EVAL_PAGE
     * @throws JspException if an error occurs while processing this tag
     *
     * @see Tag#doEndTag()
     */

    public int doEndTag() throws JspException {
	return EVAL_PAGE;
    }


    /**
     * Default processing for a body.
     *
     * @return SKIP_BODY
     * @throws JspException if an error occurs while processing this tag
     *
     * @see IterationTag#doAfterBody()
     */
    
    public int doAfterBody() throws JspException {
	return SKIP_BODY;
    }

    // Actions related to body evaluation


    /**
     * Release state.
     *
     * @see Tag#release()
     */

    public void release() {
	parent = null;
	id = null;
	if( values != null ) {
	    values.clear();
	}
	values = null;
    }

    /**
     * Set the nesting tag of this tag.
     *
     * @param t The parent Tag.
     * @see Tag#setParent(Tag)
     */

    public void setParent(Tag t) {
	parent = t;
    }

    /**
     * The Tag instance most closely enclosing this tag instance.
     * @see Tag#getParent()
     *
     * @return the parent tag instance or null
     */

    public Tag getParent() {
	return parent;
    }

    /**
     * Set the id attribute for this tag.
     *
     * @param id The String for the id.
     */

    public void setId(String id) {
	this.id = id;
    }

    /**
     * The value of the id attribute of this tag; or null.
     *
     * @return the value of the id attribute, or null
     */
    
    public String getId() {
	return id;
    }

    /**
     * Set the page context.
     *
     * @param pageContext The PageContext.
     * @see Tag#setPageContext
     */

    public void setPageContext(PageContext pageContext) {
	this.pageContext = pageContext;
    }

    /**
     * Associate a value with a String key.
     *
     * @param k The key String.
     * @param o The value to associate.
     */

    public void setValue(String k, Object o) {
	if (values == null) {
	    values = new Hashtable<String, Object>();
	}
	values.put(k, o);
    }

    /**
     * Get a the value associated with a key.
     *
     * @param k The string key.
     * @return The value associated with the key, or null.
     */

    public Object getValue(String k) {
	if (values == null) {
	    return null;
	} else {
	    return values.get(k);
	}
    }

    /**
     * Remove a value associated with a key.
     *
     * @param k The string key.
     */

    public void removeValue(String k) {
	if (values != null) {
	    values.remove(k);
	}
    }

    /**
     * Enumerate the keys for the values kept by this tag handler.
     *
     * @return An enumeration of all the keys for the values set,
     *     or null or an empty Enumeration if no values have been set.
     */

    public Enumeration<String> getValues() {
	if (values == null) {
	    return null;
	}
	return values.keys();
    }

    // private fields

    private   Tag         parent;
    private   Hashtable<String, Object> values;
    /**
     * The value of the id attribute of this tag; or null.
     */
    protected String	  id;

    // protected fields

    /**
     * The PageContext.
     */
    protected PageContext pageContext;
}

