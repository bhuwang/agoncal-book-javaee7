/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2012 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
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
 */
package org.agoncal.book.javaee7.chapter13;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@LocalBean
public class JavaEESyncReceiverNewCDIWithProperties {

  @Inject
  @JMSConnectionFactory("java:global/jms/chapter13ConnectionFactory") // <== could omit this and use the default
  private JMSContext context;

  @Resource(lookup = "java:global/jms/chapter13Queue")
  Queue chapter13Queue;

  public String receiveMessageNewCDIWithProperties() {
    try {
      JMSConsumer consumer = context.createConsumer(chapter13Queue);
      TextMessage textMessage = (TextMessage) consumer.receive(1000);
      if (textMessage == null) {
        return "Received null";
      } else {
        return "Payload=" + textMessage.getText() + ", JMSPriority=" + textMessage.getJMSPriority() + ", foo=" + textMessage.getStringProperty("foo");
      }
    } catch (Exception ex) {
      Logger.getLogger(JavaEESyncReceiverOld.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
  }
}
