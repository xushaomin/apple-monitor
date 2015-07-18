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
*//*
package com.appleframework.jmx.core.management.data.jsr77;

import com.appleframework.jmx.core.management.data.DataFormat;

import javax.management.j2ee.statistics.BoundaryStatistic;
import javax.management.j2ee.statistics.BoundedRangeStatistic;
import javax.management.j2ee.statistics.CountStatistic;
import javax.management.j2ee.statistics.RangeStatistic;
import javax.management.j2ee.statistics.Statistic;
import javax.management.j2ee.statistics.Stats;
import javax.management.j2ee.statistics.TimeStatistic;

*//**
 * Name: <stats name>
 * Description: <stats description>
 * Unit: <stats unit>
 * Start Time: <start time>
 * Last Sample Time: <last sample time>
 * <stats info>
 * 
 * TODO: Need to handle subclasses of Stats -rk 
 * <p>
 * Date:  April 14, 2006
 * @author	Rakesh Kalra
 *//*
public class StatsFormat implements DataFormat {

    public String format(Object data) {
        StringBuffer output = new StringBuffer();
        Stats stats = (Stats)data;
        for(Statistic statistic:stats.getStatistics()){
            output.append("Name: ");
            output.append(statistic.getName());
            output.append("\n");
            output.append("Description: ");
            output.append(statistic.getDescription());
            output.append("\n");
            output.append("Unit: ");
            output.append(statistic.getUnit());
            output.append("\n");
            output.append("Start Time: ");
            output.append(statistic.getStartTime());
            output.append("\n");
            output.append("Last Sample Time: ");
            output.append(statistic.getLastSampleTime());
            output.append("\n");
            output.append(getStatisticsInfo(statistic));
            output.append("\n");
            output.append("\n");
        }
        return output.toString();
    }
    
    private String getStatisticsInfo(Statistic statistic){
        if(statistic instanceof BoundaryStatistic){
            return toString((BoundaryStatistic)statistic);
        }else if(statistic instanceof BoundedRangeStatistic){
            return toString((BoundedRangeStatistic)statistic);
        }else if(statistic instanceof CountStatistic){
            return toString((CountStatistic)statistic);
        }else if(statistic instanceof BoundedRangeStatistic){
            return toString((BoundedRangeStatistic)statistic);
        }else if(statistic instanceof RangeStatistic){
            return toString((RangeStatistic)statistic);
        }else if(statistic instanceof TimeStatistic){
            return toString((TimeStatistic)statistic);
        }else{
            return statistic.toString();
        }
    }
    
    private String toString(BoundaryStatistic statistic){
        return "Low: " + statistic.getLowerBound() + " High: " + statistic.getUpperBound();
    }
    
    private String toString(BoundedRangeStatistic statistic){
        return toString((BoundaryStatistic)statistic) + " " + toString((RangeStatistic)statistic);
    }
    
    private String toString(CountStatistic statistic){
        return "Count: " + statistic.getCount();
    }
    
    private String toString(RangeStatistic statistic){
        return "Lowest: " + statistic.getLowWaterMark() + " Higest: " + statistic.getHighWaterMark();
    }
    
    private String toString(TimeStatistic statistic){
        return "Min: " + statistic.getMinTime() + " Max: " + statistic.getMaxTime();
    }
    
}
*/