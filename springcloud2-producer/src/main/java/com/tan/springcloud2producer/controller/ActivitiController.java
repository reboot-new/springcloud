package com.tan.springcloud2producer.controller;

//import com.elitel.license.LicenseController;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.node.ObjectNode;
//import io.swagger.annotations.ApiOperation;
//import org.activiti.bpmn.converter.BpmnXMLConverter;
//import org.activiti.bpmn.model.BpmnModel;
//import org.activiti.editor.constants.ModelDataJsonConstants;
//import org.activiti.editor.language.json.converter.BpmnJsonConverter;
//import org.activiti.engine.ProcessEngine;
//import org.activiti.engine.RepositoryService;
//import org.activiti.engine.RuntimeService;
//import org.activiti.engine.TaskService;
//import org.activiti.engine.impl.persistence.entity.data.ModelDataManager;
//import org.activiti.engine.repository.Deployment;
//import org.activiti.engine.repository.DeploymentBuilder;
//import org.activiti.engine.repository.Model;
//import org.activiti.engine.runtime.ProcessInstance;
//import org.activiti.engine.task.Task;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.xml.stream.XMLInputFactory;
//import javax.xml.stream.XMLStreamReader;
//import java.io.ByteArrayInputStream;
//import java.io.FileInputStream;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.List;

//@RestController
//@RequestMapping("/activiti")
public class ActivitiController {
//
//    @Autowired
//    private ProcessEngine processEngine;
//    @Autowired
//    private ObjectMapper objectMapper;
//    @Autowired
//    private RepositoryService repositoryService;
//    @Autowired
//    private RuntimeService runtimeService;
//    @Autowired
//    private TaskService taskService;
//
//    @RequestMapping("/test")
//    public String test(){
//        return "ok";
//    }
//
//    @RequestMapping("/upload")
//    public String uploadModel() throws Exception{
//        InputStream inputStream = new FileInputStream("F:\\Code\\Java\\springcloud\\springcloud2-producer\\src\\main\\resources\\processes\\SimpleUserTask\\SimpleUserTask.bpmn");
//        XMLInputFactory xif =  XMLInputFactory.newFactory();
//        XMLStreamReader xmlStreamReader =  xif.createXMLStreamReader(inputStream);
//        BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xmlStreamReader);
//
//        String processName = null;
//        if (StringUtils.isNotEmpty(bpmnModel.getMainProcess().getName())) {
//            processName = bpmnModel.getMainProcess().getName();
//        } else {
//            processName = bpmnModel.getMainProcess().getId();
//        }
//        Model modelData;
//        modelData = repositoryService.newModel();
//        ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
//        modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, processName);
//        modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
//        modelData.setMetaInfo(modelObjectNode.toString());
//        modelData.setName(processName);
//
//        repositoryService.saveModel(modelData);
//
//        BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
//        ObjectNode editorNode = jsonConverter.convertToJson(bpmnModel);
//
//        repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
//
////        repositoryService.saveModel(bpmnModel);
//        return modelData.getId();
//    }
//
//    @RequestMapping("/new")
//    public String newModele(){
//        Model model = repositoryService.newModel();
//        //设置一些默认信息，可以用参数接收
//        String name = "new-process";
//        String description = "";
//        int revision = 1;
//        String key = "process";
//
//        ObjectNode modelNode = objectMapper.createObjectNode();
//        modelNode.put(ModelDataJsonConstants.MODEL_NAME, name);
//        modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
//        modelNode.put(ModelDataJsonConstants.MODEL_REVISION, revision);
//
//        model.setName(name);
//        model.setKey(key);
//        model.setMetaInfo(modelNode.toString());
//        repositoryService.saveModel(model);
//        return model.getId();
//    }
//
//    @RequestMapping("/start")
//    public void startProcess(String id){
////        ProcessInstance instance = runtimeService.startProcessInstanceByKey("simpleUserTask", "1");
//        ProcessInstance instance = runtimeService.startProcessInstanceById(id);
//        System.out.println("Id: " + instance.getId());
//    }
//
//    @RequestMapping("/query")
//    public void queryTask(String user) {
//        List<Task> tasks = taskService.createTaskQuery().taskAssignee(user)
//                // 分页查询
//                // .listPage(firstResult, maxResults)
//                // 排序
//                // .orderByTaskCreateTime().desc()
//                // 如果你知道这个查询是一条记录的话, 可以使用 .singleResult() 方法来获取单一的记录
//                // .singleResult()
//                .list();
//        for (Task task : tasks) {
//            System.out.println(task.toString()); // Task[id=2505, name=提交请假]
//        }
//    }
//
////    @ApiOperation(value = "发布模型为流程定义")
//    @RequestMapping("/deployment")
//    public String deploy(String id) throws Exception {
//        //获取模型
//        RepositoryService repositoryService = processEngine.getRepositoryService();
//        Model modelData = repositoryService.getModel(id);
//        byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());
//        if (bytes == null) {
//            return "模型数据为空，请先设计流程并成功保存，再进行发布。";
//        }
//        JsonNode modelNode = new ObjectMapper().readTree(bytes);
//        BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
//        if (model.getProcesses().size() == 0) {
//            return "数据模型不符要求，请至少设计一条主线流程。";
//        }
//        byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);
//
//        //发布流程
//        String processName = modelData.getName() + ".bpmn20.xml";
//        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment()
//                .name(modelData.getName())
//                .addString(processName, new String(bpmnBytes, "UTF-8"));
//        Deployment deployment = deploymentBuilder.deploy();
//        modelData.setDeploymentId(deployment.getId());
//        repositoryService.saveModel(modelData);
//        return deployment.getId();
//    }
}
