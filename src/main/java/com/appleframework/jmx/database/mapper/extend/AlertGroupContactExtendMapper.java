package com.appleframework.jmx.database.mapper.extend;

import com.appleframework.jmx.database.entity.AlertContactEntity;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertGroupContactExtendMapper {
	

    List<AlertContactEntity> selectAlertContactByAlertGroupId(Integer alertGroupId);

    
}