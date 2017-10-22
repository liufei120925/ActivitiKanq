package com.kanq.platform.dao;

import java.util.List;

import com.kanq.platform.bean.FormBean;

public interface ActivitiDao {
	
	public int  deleteById(String id) throws Exception;
	
	public int  updateFormById(FormBean form) throws Exception;

	public int  updateNodeById(String processId,String taskId, String assignee, String defination, String name, int process, String nodepeople, String nodeSP1, String nodeSP2) throws Exception;

	public int goUpdatePreNode(FormBean leave) throws Exception;
	
	public int  insert(FormBean form) throws Exception;
	
	public List<FormBean>  selectById(String id) throws Exception;

	public List<FormBean> queryAllByName(String applicant) throws Exception;
	
	public List<FormBean> queryAllByNodeName(String assignee) throws Exception;
//	public List<FormBean> queryAllByName(String assignee) throws Exception;
//	public List<FormBean> queryAllByName(String assignee) throws Exception;
//	public List<FormBean> queryAllByName(String assignee) throws Exception;

	public List<FormBean> findDBByLoginName(String assignee) throws Exception;

	public List<FormBean> findYBByLoginName(String assignee) throws Exception;

	public List<FormBean> findBJByLoginName(String assignee) throws Exception;

	public FormBean findByUuid(String uuid) throws Exception;

	public int updateSave(FormBean leave) throws Exception;

}
