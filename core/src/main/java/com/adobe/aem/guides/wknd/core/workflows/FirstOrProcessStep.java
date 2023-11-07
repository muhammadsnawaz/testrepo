package com.adobe.aem.guides.wknd.core.workflows;

import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.exec.WorkItem;
import com.day.cq.workflow.exec.WorkflowData;
import com.day.cq.workflow.exec.WorkflowProcess;
import com.day.cq.workflow.metadata.MetaDataMap;

@Component(service = WorkflowProcess.class,immediate=true, property = {"process.label=First Or Process Step" })
public class FirstOrProcessStep implements WorkflowProcess {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	public void execute(WorkItem workItem, WorkflowSession wfSession, MetaDataMap args)
	{
		WorkflowData wfData = workItem.getWorkflowData();
		String passedValue = workItem.getWorkflowData().getMetaDataMap().get("passingValues",java.lang.String.class);
		log.debug("passed value from the first process step is :: "+passedValue);
		String pagePath = (String) wfData.getPayload();
		log.debug("First Or Process Step -- Inside the workflow ");
		log.debug("First Or Process Step -- Page path or payload in workflow is :: "+pagePath);
	}
}

