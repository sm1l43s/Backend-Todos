package by.program.restAPI.rest;

import by.program.restAPI.dto.TaskDto;
import by.program.restAPI.dto.forUserDto.AddTaskDto;
import by.program.restAPI.dto.forUserDto.UpdateTaskDto;
import by.program.restAPI.model.Status;
import by.program.restAPI.model.Task;
import by.program.restAPI.model.User;
import by.program.restAPI.responseEntity.CommonResponse;
import by.program.restAPI.responseEntity.ResponseFromServer;
import by.program.restAPI.responseEntity.ResponseListData;
import by.program.restAPI.service.TaskService;
import by.program.restAPI.service.UserService;
import by.program.restAPI.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/users/")
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;
    private final Utils utils;

    @Autowired
    public TaskController(TaskService taskService, UserService userService, Utils utils) {
        this.taskService = taskService;
        this.userService = userService;
        this.utils = utils;
    }

    @RequestMapping(value = "tasks", method = RequestMethod.GET)
    public ResponseEntity getTaskUser(@RequestHeader("Authorization") String bearerToken,
                                      @RequestParam(name = "page", defaultValue = "1") int page,
                                      @RequestParam(name = "size", defaultValue = "10") int size) {
        CommonResponse response = null;
        User user = utils.getUser(bearerToken);
        Page<Task> data = taskService.getAllTaskByUserAndStatusNot(user, PageRequest.of(page - 1, size),
                Status.DELETED);;

        if (data.isEmpty() || size > 100) {
            response = new CommonResponse(HttpStatus.NOT_FOUND, null, "Not found tasks", 1);
            return ResponseFromServer.returnResult(response, HttpStatus.NOT_FOUND);
        }

        response = new ResponseListData(HttpStatus.OK, TaskDto.fromListTaskToListTaskDto(data.getContent()),
                "", 0, data.getTotalElements());
        return ResponseFromServer.returnResult(response, HttpStatus.OK);
    }

    @RequestMapping(value = "tasks/{idTask}", method = RequestMethod.GET)
    public ResponseEntity getTaskById(@RequestHeader("Authorization") String bearerToken,
                                      @PathVariable("idTask") long idTask) {
        CommonResponse response = null;
        User user = utils.getUser(bearerToken);
        Task task = taskService.getTaskByIdAndStatusNot(idTask, Status.DELETED);

        if (task == null || task.getUser().getId() != user.getId()) {
            response = new CommonResponse(HttpStatus.NO_CONTENT, null,
                    "Task with id: " + idTask + " not found or task is not yours", 1);
            return ResponseFromServer.returnResult(response, HttpStatus.OK);
        }

        response = new CommonResponse(HttpStatus.OK, TaskDto.fromTaskToTaskDto(task), "", 0);
        return ResponseFromServer.returnResult(response, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}/tasks", method = RequestMethod.GET)
    public ResponseEntity getTaskUser(@PathVariable("id") long id,
                                      @RequestParam(name = "page", defaultValue = "1") int page,
                                      @RequestParam(name = "size", defaultValue = "50") int size,
                                      @RequestParam(name = "status", defaultValue = "") String status,
                                      @RequestParam(name = "typeOrder", defaultValue = "asc") String typeOrder,
                                      @RequestParam(name = "orderFields", defaultValue = "id") String orderFields,
                                      @RequestParam(name = "search", defaultValue = "") String search) {
        CommonResponse response = null;
        User user = userService.findByIdAndStatusNot(id, Status.DELETED);
        Page<Task> data = null;

        if (search.isEmpty()) {
            data = pageHelper(user, page, size, status, typeOrder, orderFields);
        } else {
            data = taskService.findAllByUserAndStatusNotAndTitleContaining(user, PageRequest.of(page - 1, size),
                    Status.DELETED, search);
        }

        if (user == null || data.isEmpty()) {
            response = new CommonResponse(HttpStatus.NOT_FOUND, null, "Not found tasks by user id: " + id, 1);
            return ResponseFromServer.returnResult(response, HttpStatus.OK);
        }

        response = new ResponseListData(HttpStatus.OK, TaskDto.fromListTaskToListTaskDto(data.getContent()),
                "", 0, data.getTotalElements());
        return ResponseFromServer.returnResult(response, HttpStatus.OK);
    }

    private Page<Task> pageHelper(User user, int page, int size, String status, String typeOrder, String orderFields) {
        Page<Task> data = null;

        if (typeOrder.equals("asc")) {
            if (status.equals("all") || status.isEmpty()) {
                data = taskService.getAllTaskByUserAndStatusNot(user, PageRequest.of(page - 1, size,
                        Sort.by(orderFields).ascending()), Status.DELETED);
            }
            if (status.equals("ACTIVE")) {
                data = taskService.findAllByUserAndStatusAndStatusNot(user, PageRequest.of(page - 1, size,
                        Sort.by(orderFields).ascending()), Status.ACTIVE, Status.DELETED);
            }
            if (status.equals("COMPLETED")) {
                data = taskService.findAllByUserAndStatusAndStatusNot(user, PageRequest.of(page - 1, size,
                        Sort.by(orderFields).ascending()), Status.COMPLETED, Status.DELETED);
            }
        } else if (typeOrder.equals("desc")) {
            if (status.equals("all") || status.isEmpty()) {
                data = taskService.getAllTaskByUserAndStatusNot(user, PageRequest.of(page - 1, size,
                        Sort.by(orderFields).descending()), Status.DELETED);
            }
            if (status.equals("ACTIVE")) {
                data = taskService.findAllByUserAndStatusAndStatusNot(user, PageRequest.of(page - 1, size,
                        Sort.by(orderFields).descending()), Status.ACTIVE, Status.DELETED);
            }
            if (status.equals("COMPLETED")) {
                data = taskService.findAllByUserAndStatusAndStatusNot(user, PageRequest.of(page - 1, size,
                        Sort.by(orderFields).descending()), Status.COMPLETED, Status.DELETED);
            }
        }
        return data;
    }

    @RequestMapping(value = "tasks", method = RequestMethod.POST)
    public ResponseEntity addTaskByUser(@RequestHeader("Authorization") String bearerToken,
                                        @RequestBody AddTaskDto taskDto) {
        User user = utils.getUser(bearerToken);
        Task task = new Task(taskDto.getTitle(), taskDto.getDescription(),
                taskDto.getStartDate(), taskDto.getEndDate(), user);
        Task addTask = taskService.add(task);
        CommonResponse response = new CommonResponse(HttpStatus.OK, TaskDto.fromTaskToTaskDto(addTask), "", 0);
        return ResponseFromServer.returnResult(response, HttpStatus.OK);
    }

    @RequestMapping(value = "tasks/{idTask}", method = RequestMethod.PUT)
    public ResponseEntity updateTaskById(@RequestHeader("Authorization") String bearerToken,
                                         @PathVariable("idTask") long idTask,
                                         @RequestBody UpdateTaskDto updateTaskDto) {
        CommonResponse response = null;
        User user = utils.getUser(bearerToken);
        Task taskBeforeUpdate = taskService.getTaskByIdAndStatusNot(idTask, Status.DELETED);

        if (taskBeforeUpdate == null || taskBeforeUpdate.getUser().getId() != user.getId()) {
            response = new CommonResponse(HttpStatus.NO_CONTENT, null,
                    "Task with id: " + idTask + " not found or task is not yours", 1);
            return ResponseFromServer.returnResult(response, HttpStatus.OK);
        }

        taskBeforeUpdate.setStatus(updateTaskDto.getStatus());
        taskBeforeUpdate.setTitle(updateTaskDto.getTitle());
        taskBeforeUpdate.setDescription(updateTaskDto.getDescription());
        taskBeforeUpdate.setStartDate(updateTaskDto.getStartDate());
        taskBeforeUpdate.setEndDate(updateTaskDto.getEndDate());

        Task afterUpdate = taskService.update(taskBeforeUpdate);
        response = new CommonResponse(HttpStatus.OK, TaskDto.fromTaskToTaskDto(afterUpdate), "", 0);
        return ResponseFromServer.returnResult(response, HttpStatus.OK);
    }

    @RequestMapping(value = "tasks/{idTask}", method = RequestMethod.DELETE)
    public ResponseEntity deleteTaskById(@RequestHeader("Authorization") String bearerToken,
                                         @PathVariable("idTask") long idTask) {

        CommonResponse response = null;
        User user = utils.getUser(bearerToken);
        Task task = taskService.getTaskByIdAndStatusNot(idTask, Status.DELETED);

        if (task == null || task.getUser().getId() != user.getId()) {
            response = new CommonResponse(HttpStatus.NO_CONTENT, null,
                    "Task with id: " + idTask + " not found or task is not yours", 1);
            return ResponseFromServer.returnResult(response, HttpStatus.OK);
        }

        task.setStatus(Status.DELETED);
        taskService.update(task);

        response = new CommonResponse(HttpStatus.OK, null, "Task with id: " + idTask + " deleted", 0);
        return ResponseFromServer.returnResult(response, HttpStatus.OK);
    }
}
