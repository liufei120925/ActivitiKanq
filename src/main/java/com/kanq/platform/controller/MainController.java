package com.kanq.platform.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kanq.platform.bean.FormBean;
import com.kanq.platform.bean.LeaveBean;
import com.kanq.platform.service.ActivitiService;

@Controller(value="ActivitiController")
@RequestMapping("Test")
public class MainController {

	@Autowired
	private ActivitiService activitiService;
	
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();  

	@RequestMapping("add")
	public ModelAndView addMainView( String type,String param,FormBean form) throws Exception{

		Map<String, Object> map = new HashMap<>();
		addProcess(map,param,form);
		ModelAndView model = new ModelAndView("Cont");
		for (Entry<String, Object> iterable_element : map.entrySet()) {

			model.addObject(iterable_element.getKey(), iterable_element.getValue());
		}
		return model;
		
	}	
//	@RequestMapping("delete")
//	public ModelAndView deleteMainView( String id) throws Exception{
//
//		Map<String, Object> map = new HashMap<>();
//		deleteProcess(map);
//		ModelAndView model = new ModelAndView("Cont");
//		for (Entry<String, Object> iterable_element : map.entrySet()) {
//
//			model.addObject(iterable_element.getKey(), iterable_element.getValue());
//		}
//		return model;
//		
//	}	
	
	@RequestMapping("select")
	public ModelAndView findMainView( String type,String param,FormBean form) throws Exception{

		Map<String, Object> map = new HashMap<>();
		findProcess(map,param);
		ModelAndView model = new ModelAndView("Cont");
		for (Entry<String, Object> iterable_element : map.entrySet()) {

			model.addObject(iterable_element.getKey(), iterable_element.getValue());
		}
		return model; 
	}	
	
	@RequestMapping("update")
	public ModelAndView updateMainView( String type,String param,FormBean form) throws Exception{

		Map<String, Object> map = new HashMap<>();
		commitProcess(map,param,form); 
		ModelAndView model = new ModelAndView("Cont");
		for (Entry<String, Object> iterable_element : map.entrySet()) {

			model.addObject(iterable_element.getKey(), iterable_element.getValue());
		}
		return model;
		
	}
	@RequestMapping("Cont")
	public ModelAndView getHtml( String type,String param,FormBean form) throws Exception{
		Map<String, Object> map = new HashMap<>();
		ModelAndView model = new ModelAndView("Cont");
		 
		if (type!=null) {

			if (type.equals("add")) {//添加
				addProcess(map,param,form);
				
			}else if (type.equals("update")) {//删除
				
				commitProcess(map,param,form); 
				
			}else if (type.equals("find")) {//查询
				
				findProcess(map,param);
				
			} 
			
			for (Entry<String, Object> iterable_element : map.entrySet()) {

				model.addObject(iterable_element.getKey(), iterable_element.getValue());
			}
		}
		return model;
		
	}

	/**
	 * 查询
	 * @param map
	 * @param param 经办人
	 */
	private Map<String, Object> findProcess(Map<String, Object> map, String assignee) {

		List<LeaveBean> arrLeave = new ArrayList<>();
		List<Task> list = processEngine.getTaskService()//与正在执行的任务管理相关的Service  
				.createTaskQuery()//创建任务查询   
				.taskAssignee(assignee)//指定个人任查询，指定办理人   
				.list();
		
		if (list!=null&&list.size()>0) {
			for (Task task : list) {

				LeaveBean leave = new LeaveBean();
				leave.setAssignee(task.getAssignee() ); 
				leave.setCreateDate(task.getCreateTime());
				leave.setExecutionId(task.getExecutionId());
				leave.setId(task.getId());//DB id of the task.
				leave.setName(task.getName());//Name or title of the task.
				leave.setProcessDefinitionId(task.getProcessDefinitionId());
				leave.setProcessInstanceId(task.getProcessInstanceId());
				leave.setProcessInstanceId(task.getFormKey());//The form key for the user task
				arrLeave.add(leave);

			} 
		} 
		map.put("leaveList", arrLeave);
		map.put("type", "query");

//		activitiService.selectById(task.getId());
		
		return map;
	} 
	/**
	 * 查询
	 * @param map
	 * @param param 经办人
	 */
	private Map<String, Object> findProcessbyId(Map<String, Object> map, String param,String type) {
		 
		List<Task> list = null;
		List<LeaveBean> arrLeave = new ArrayList<>();
		if (type.equals("name")) {
			list= processEngine.getTaskService()//与正在执行的任务管理相关的Service  
					.createTaskQuery()//创建任务查询   
					.taskName(param) //根据任务名称查询
					.list();
		} else if(type.equals("id")){
			list= processEngine.getTaskService()//与正在执行的任务管理相关的Service  
					.createTaskQuery()//创建任务查询   
					.taskId(param)
					.list();
		} else if(type.equals("assignee")){
			list= processEngine.getTaskService()//与正在执行的任务管理相关的Service  
					.createTaskQuery()//创建任务查询   
					.taskAssignee(param)
					.list();
		}
		
		if (list!=null&&list.size()>0) {
			for (Task task : list) {

				LeaveBean leave = new LeaveBean();
				leave.setAssignee(task.getAssignee() ); 
				leave.setCreateDate(task.getCreateTime());
				leave.setExecutionId(task.getExecutionId());
				leave.setId(task.getId());
				leave.setName(task.getId());
				leave.setProcessDefinitionId(task.getProcessDefinitionId());
				leave.setProcessInstanceId(task.getProcessInstanceId());
				arrLeave.add(leave);
			}
		} 
		map.put("leaveList", arrLeave);
		map.put("type", "query");
		return map;
	} 
	
	/**
	 * 删除
	 * @param map
	 * @param taskId 任务id
	 * @return 
	 * @throws Exception 
	 */
	private Map<String, Object> commitProcess(Map<String, Object> map, String taskId,FormBean form) throws Exception {


		processEngine.getTaskService().complete(taskId);//与正在执行的任务管理相关的Service  
		map = findProcessbyId(map, taskId, "id");
		map.put("remark", "完成任务，任务id:"+taskId); 

		activitiService.updateFormById(form);
		return map;
	}
	
//	/**
//	 * 删除
//	 * @param map
//	 * @return 
//	 * @throws Exception 
//	 */
//	private Map<String, Object> deleteProcess(Map<String, Object> map) throws Exception {
//
//
//		processEngine.getTaskService().complete(taskId);//与正在执行的任务管理相关的Service  
//		map = findProcessbyId(map, taskId, "id");
//		map.put("remark", "完成任务，任务id:"+taskId); 
//
//		activitiService.deleteById(form.getId());
//		return map;
//	}
	/**
	 * 添加
	 * @param map
	 * @param param 添加部署流程的名称
	 * @throws Exception 
	 */
	private Map<String, Object> addProcess(Map<String, Object> map, String param,FormBean form) throws Exception {

		Deployment deployment = processEngine.getRepositoryService()//与流程定义和部署对象相关的Service  
				.createDeployment()//创建一个部署对象  
				.name(param)//添加部署的名称  
				.addClasspathResource("diagrams/leave.bpmn")//从classpath的资源中加载，一次只能加载一个文件  
				.addClasspathResource("diagrams/leave.png")//从classpath的资源中加载，一次只能加载一个文件  
				.deploy();//完成部署  
		System.out.println("部署id:"+deployment.getId());		  //部署id:2501 
		System.out.println("部署名称："+deployment.getName());   //部署名称：leave请假流程
		System.out.println("Category："+deployment.getCategory());   //部署名称：leave请假流程
		System.out.println("TenantId："+deployment.getTenantId());   //部署名称：leave请假流程
		System.out.println("DeploymentTime："+deployment.getDeploymentTime());   //部署名称：leave请假流程 

		map.put("id", "部署id:"+deployment.getId());
		map.put("name", "部署名称："+deployment.getName());

		String processDefinitionKey = "leave"; 
		ProcessInstance pi = processEngine.getRuntimeService()//与正在执行   的流程实例和执行对象相关的Service  
				.startProcessInstanceByKey(processDefinitionKey);//使用流程定义的key启动流程实例,key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动  
		System.out.println("流程实例id:"+pi.getId());//流程实例id:30001
		System.out.println("流程定义id:"+pi.getProcessInstanceId()); //流程定义id:30001
		
		map.put("lcslId", "流程实例id:"+pi.getId());
		map.put("lcdyId", "流程定义id:"+pi.getProcessInstanceId());
		map.put("reamark", "添加完毕,操作类型：添加");
		map.put("type", "add");

		int num = activitiService.insert(form);//保存入数据库
		map.put("param", num);
		
		return map;
	}
}













