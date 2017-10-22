package com.kanq.platform.mapper;

import java.util.List; 

import org.apache.ibatis.annotations.Param;

import com.kanq.platform.bean.FormBean;

public interface ActivitiMapper {

	public int insertForm(FormBean form)throws Exception;
	
	public int updateFormById(FormBean form)throws Exception;

	public int updateNodeById(@Param("processId")String processId,@Param("taskId")String taskId, @Param("assignee")String assignee,
			@Param("definition")String definition, @Param("name")String name,  @Param("process")int process, 
			@Param("nodepeople")String nodepeople, @Param("nodeSP1")String nodeSP1, @Param("nodeSP2")String nodeSP2)throws Exception; 

	public int updateSave(FormBean leave)throws Exception;
	
	public List<FormBean> findById(@Param("id")String id); 
	
	public int deleteById(@Param("uuid")String uuid);

	public List<FormBean> queryAllByName(String applicant)throws Exception;

	public List<FormBean> queryAllByNodeName(String assignee)throws Exception;

	public List<FormBean> findDBByLoginName(String assignee)throws Exception;

	public List<FormBean> findYBByLoginName(String assignee)throws Exception;

	public List<FormBean> findBJByLoginName(String assignee)throws Exception;

	public FormBean findByUuid(String uuid)throws Exception;

	public int goUpdatePreNode(FormBean leave)throws Exception;
}
