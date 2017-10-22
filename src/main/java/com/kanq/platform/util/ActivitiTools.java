package com.kanq.platform.util;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;

public class ActivitiTools {

	private static ActivitiTools activitiTools;
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();   
	RuntimeService runtimeService = processEngine.getRuntimeService();
	RepositoryService repositoryService = processEngine.getRepositoryService();
	TaskService taskService = processEngine.getTaskService();
	ManagementService managementService = processEngine.getManagementService();
	IdentityService identityService = processEngine.getIdentityService();
	HistoryService historyService = processEngine.getHistoryService();
	FormService formService = processEngine.getFormService();
	
	public static ActivitiTools getInstance(){
		
		if (activitiTools==null) {
			return new ActivitiTools();
		}
		return activitiTools;
		
	}
	/**
	 * 查询流程是否走完
	 * @param processId
	 * @return
	 */
	public HistoricProcessInstance findByHistory(String processId) {
		
		HistoricProcessInstance hpi = null;
		/**判断流程是否结束，查询正在执行的执行对象表*/
        ProcessInstance rpi = processEngine.getRuntimeService()//
                        .createProcessInstanceQuery()//创建流程实例查询对象
                        .deploymentId(processId)
                        .singleResult();
        //说明流程实例结束了
        if(rpi==null){
            /**查询历史，获取流程的相关信息*/
            hpi = processEngine.getHistoryService()//
                        .createHistoricProcessInstanceQuery()//
                        .deploymentId(processId)//使用流程实例ID查询
                        .singleResult(); 
        }
		return hpi;
	}	
	/**
	 * 返回上一节点
	 */
	public void a(){

		String taskId = "3";
	    //根据要跳转的任务ID获取其任务
	    HistoricTaskInstance hisTask = historyService
	                .createHistoricTaskInstanceQuery().taskId(taskId)
	                .singleResult();
	    //进而获取流程实例
	    ProcessInstance instance = runtimeService
	                .createProcessInstanceQuery()
	                .processInstanceId(hisTask.getProcessInstanceId())
	                .singleResult();
	    //取得流程定义
	    ProcessDefinitionEntity definition = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(hisTask.getProcessDefinitionId());
	    //获取历史任务的Activity
	    ActivityImpl hisActivity = definition.findActivity(hisTask.getTaskDefinitionKey());
	    //实现跳转
	    managementService.executeCommand(new JumpCmd(instance.getId(), hisActivity.getId()));
	     
	}
}
