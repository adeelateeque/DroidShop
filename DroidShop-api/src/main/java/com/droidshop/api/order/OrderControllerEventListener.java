/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.droidshop.api.order;

import org.springframework.data.rest.repository.context.AbstractRepositoryEventListener;
import org.springframework.stereotype.Component;

import com.droidshop.api.model.order.Order;

/**
 * Event listener to reject {@code DELETE} requests to Spring Data REST.
 * 
 * @author Oliver Gierke
 */
@Component
@SuppressWarnings("rawtypes")
class OrderControllerEventListener extends AbstractRepositoryEventListener {

	/* 
	 * (non-Javadoc)
	 * @see org.springframework.data.rest.repository.context.AbstractRepositoryEventListener#onBeforeDelete(java.lang.Object)
	 */
	@Override
	protected void onBeforeDelete(Object entity) {

		if (entity instanceof Order) {

			Order order = (Order) entity;

			if (order.isPaid()) {
				throw new OrderAlreadyPaidException();
			}
		}
	}
}
