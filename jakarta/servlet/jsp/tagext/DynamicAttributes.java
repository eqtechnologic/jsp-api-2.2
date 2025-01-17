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

import jakarta.servlet.jsp.JspException;

/**
 * For a tag to declare that it accepts dynamic attributes, it must implement
 * this interface.  The entry for the tag in the Tag Library Descriptor must 
 * also be configured to indicate dynamic attributes are accepted.
 * <br>
 * For any attribute that is not declared in the Tag Library Descriptor for
 * this tag, instead of getting an error at translation time, the 
 * <code>setDynamicAttribute()</code> method is called, with the name and
 * value of the attribute.  It is the responsibility of the tag to 
 * remember the names and values of the dynamic attributes.
 *
 * @since JSP 2.0
 */
public interface DynamicAttributes {
    
    /**
     * Called when a tag declared to accept dynamic attributes is passed
     * an attribute that is not declared in the Tag Library Descriptor.
     * 
     * @param uri the namespace of the attribute, or null if in the default
     *     namespace.
     * @param localName the name of the attribute being set.
     * @param value the value of the attribute
     * @throws JspException if the tag handler wishes to
     *     signal that it does not accept the given attribute.  The 
     *     container must not call doStartTag() or doTag() for this tag.
     */
    public void setDynamicAttribute(
        String uri, String localName, Object value ) 
        throws JspException;
    
}
