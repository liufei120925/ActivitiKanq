package com.kanq.platform.service;

import java.util.List;

import com.kanq.platform.bean.FormBean;
import com.kanq.platform.bean.LeaveBean;

public interface ActivitiService {

	public List<FormBean>  selectById(String id) throws Exception;
	
	public int  deleteById(String id) throws Exception;
	/**
	 * 更新所有信息
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public int  updateFormById(FormBean form) throws Exception;

	/**
	 * 更新部分信息
	 * @param processId
	 * @param assignee
	 * @param name
	 * @param defination 
	 * @param string 
	 * @param process 
	 * @param nodeSP2 
	 * @param nodeSP1 
	 * @param string 
	 * @return 
	 * @throws Exception 
	 */
	public int updateNodeById(String processId,String taskId, String assignee, String defination, String name, int process, String nodepeople, String nodeSP1, String nodeSP2) throws Exception;
	
	public int  insert(FormBean form) throws Exception;

	/**
	 * 根据代理人查询所有任务信息
	 * @param assignee
	 * @return
	 * @throws Exception
	 */
	public List<FormBean> queryAllByNodeName(String assignee) throws Exception;
	
	/**
	 * 根据申请人查询所有任务信息
	 * @param assignee
	 * @return
	 * @throws Exception
	 */
	public List<FormBean> queryAllByName(String applicant) throws Exception;

	/**
	 * 根据登陆人查询待办信息（保存信息）
	 * @param assignee
	 * @return 
	 * @throws Exception 
	 */
	public List<FormBean> findDBByLoginName(String assignee) throws Exception;

	/**
	 * 根据登陆人查询已办信息（自己已办结且已办理到下一节点信息）
	 * @param assignee
	 */
	public List<FormBean> findYBByLoginName(String assignee) throws Exception;

	/**
	 * 所有流程执行完毕的办理信息
	 * @param assignee
	 */
	public List<FormBean> findBJByLoginName(String assignee) throws Exception;

	public FormBean findByUuid(String uuid) throws Exception;

	public int updateSave(FormBean leave) throws Exception;

	/**
	 * 申请未通过，返回申请人
	 * @param leave
	 * @return
	 * @throws Exception
	 */
	public int goUpdatePreNode(FormBean leave) throws Exception;
	
//	public List<FormBean> queryByName(String assignee) throws Exception;
//	
//	public List<FormBean> queryByName(String assignee) throws Exception;
//	
//	public List<FormBean> queryByName(String assignee) throws Exception;
}
