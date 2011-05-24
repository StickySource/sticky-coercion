/**
 * Copyright (c) 2011 RedEngine Ltd, http://www.redengine.co.nz. All rights reserved.
 *
 * This program is licensed to you under the Apache License Version 2.0,
 * and you may not use this file except in compliance with the Apache License Version 2.0.
 * You may obtain a copy of the Apache License Version 2.0 at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the Apache License Version 2.0 is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Apache License Version 2.0 for the specific language governing permissions and limitations there under.
 */
package net.stickycode.coercion.ws;

import java.net.URL;

import javax.jws.WebService;

import net.stickycode.exception.TransientException;


@SuppressWarnings("serial")
public class CouldNotConnectToWebServiceException
    extends TransientException {

  public CouldNotConnectToWebServiceException(Throwable t, URL wsdlDocumentLocation, WebService annotation) {
    super(t, "Could not connect to ws at '{}' described by '{}'", wsdlDocumentLocation, annotation);
  }

}
