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
package com.appleframework.jmx.core.services;

import java.util.List;

import com.appleframework.jmx.core.alert.AlertInfo;

/**
 *
 * Date:  Aug 2, 2005
 * @author	Rakesh Kalra
 */
public interface AlertService {

    /**
     *
     * @param context
     * @return list of AlertInfo objects
     * @throws ServiceException
     */
    public List<AlertInfo> getConsoleAlerts(ServiceContext context) throws ServiceException;

    public void removeConsoleAlert(ServiceContext context, String alertId);

}
