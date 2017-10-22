package com.kanq.platform.dao.imp;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository; 

import com.kanq.platform.bean.FormBean;
import com.kanq.platform.dao.ActivitiDao;
import com.kanq.platform.mapper.ActivitiMapper;

@Repository
public class ActivitiDaoImp implements ActivitiDao{
 
	@Autowired
	private ActivitiMapper activitiMapper;

	@Override
	public int deleteById(String id) {
		int delNum = activitiMapper.deleteById(id);
		return delNum;
	}

	@Override
	public int updateSave(FormBean leave) throws Exception { 
		
		int i = activitiMapper.updateSave(leave);
		return i; 
	}
	
	@Override
	public int updateFormById(FormBean form) throws Exception { 
		int updeteNum = activitiMapper.updateFormById(form);
		return updeteNum;
	}
	
	@Override
	public int updateNodeById(String processId,String taskId, String assignee,String defination, String name,int process, String nodepeople,String nodeSP1,String nodeSP2) throws Exception {

		int num = activitiMapper.updateNodeById(processId,taskId,assignee,defination,name,process,nodepeople,nodeSP1,nodeSP2);
		return num;
	}

	@Override
	public int insert(FormBean form) throws Exception {
		
		int i = activitiMapper.insertForm(form);
		return i; 
	}

	@Override
	public List<FormBean> selectById(String id) throws Exception {
		List<FormBean> list = activitiMapper.findById(id);
		return list;
	}

	@Override
	public List<FormBean> queryAllByName(String applicant) throws Exception {
		List<FormBean> list = activitiMapper.queryAllByName(applicant);  
		return list;
	}

	@Override
	public List<FormBean> queryAllByNodeName(String assignee) throws Exception {

		List<FormBean> list = activitiMapper.queryAllByNodeName(assignee);  
		return list;
	}

	@Override
	public List<FormBean> findDBByLoginName(String assignee) throws Exception {
		return activitiMapper.findDBByLoginName(assignee);  
	}

	@Override
	public List<FormBean> findYBByLoginName(String assignee) throws Exception {
		return activitiMapper.findYBByLoginName(assignee);  
	}

	@Override
	public List<FormBean> findBJByLoginName(String assignee) throws Exception {
		return activitiMapper.findBJByLoginName(assignee);  
	}

	@Override
	public FormBean findByUuid(String uuid) throws Exception { 
		return activitiMapper.findByUuid(uuid);
	}

	@Override
	public int goUpdatePreNode(FormBean leave) throws Exception { 
		return activitiMapper.goUpdatePreNode(leave);
	}


	
	
}
