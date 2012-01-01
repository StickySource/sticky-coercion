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

import static org.fest.assertions.Assertions.assertThat;

import javax.jws.WebService;
import javax.xml.ws.Endpoint;

import net.stickycode.coercion.target.CoercionTargets;

import org.junit.Test;

public class WebServiceCoercionTest {

  private static final String WS_URL = "http://localhost:24554/test";

  @WebService(serviceName = "TestWsService", portName = "TestWs", name = "TestWs")
  public final class WhatTestWs
      implements TestWs {

    int counter = 0;

    @Override
    public String giveMe() {
      counter++;
      return Integer.toString(counter);
    }
  }

  @WebService
  public static interface TestWs {

    String giveMe();
  }

  @WebService(name = "TestWs")
  private static interface OtherWs {

    String dontGiveMe();
  }

  @Test
  public void isApplicable() {
    assertThat(new WebServiceCoercion().isApplicableTo(CoercionTargets.find(TestWs.class))).isTrue();
  }

  @Test
  public void notApplicable() {
    assertThat(new WebServiceCoercion().isApplicableTo(CoercionTargets.find(Integer.class))).isFalse();
  }

  @Test(expected = RuntimeException.class)
  public void coerce() {
    TestWs coerced = (TestWs) new WebServiceCoercion().coerce(CoercionTargets.find(TestWs.class), null);
    assertThat(coerced).isNotNull();
  }

  @Test
  public void coerceit() {
    WhatTestWs implementor = new WhatTestWs();
    Endpoint endpoint = Endpoint.publish(WS_URL, implementor);
    try {
      TestWs coerced = (TestWs) new WebServiceCoercion()
          .coerce(CoercionTargets.find(TestWs.class), WS_URL);
      assertThat(coerced).isNotNull();
      assertThat(coerced.giveMe()).isEqualTo("1");
      assertThat(coerced.giveMe()).isEqualTo("2");
      assertThat(implementor.counter).isEqualTo(2);
    }
    finally {
      endpoint.stop();
    }
  }

  @Test(expected = CouldNotConnectToWebServiceException.class)
  public void coerceWrongUrl() {
    WhatTestWs implementor = new WhatTestWs();
    Endpoint endpoint = Endpoint.publish(WS_URL + "/moved", implementor);
    try {
      new WebServiceCoercion().coerce(CoercionTargets.find(TestWs.class), WS_URL);
    }
    finally {
      endpoint.stop();
    }
  }

  @Test(expected = CouldNotCreateServiceProxyException.class)
  public void coerceOther() {
    WhatTestWs implementor = new WhatTestWs();
    Endpoint endpoint = Endpoint.publish(WS_URL, implementor);
    try {
      new WebServiceCoercion().coerce(CoercionTargets.find(OtherWs.class), WS_URL);
    }
    finally {
      endpoint.stop();
    }
  }

  @Test(expected = CouldNotConnectToWebServiceException.class)
  public void coerceNothing() {
    new WebServiceCoercion().coerce(CoercionTargets.find(OtherWs.class), WS_URL);
  }

  @Test(expected = CouldNotConnectToWebServiceException.class)
  public void coerceWhere() {
    new WebServiceCoercion().coerce(CoercionTargets.find(OtherWs.class), "http://where");
  }

}
