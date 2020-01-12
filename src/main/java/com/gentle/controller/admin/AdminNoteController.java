package com.gentle.controller.admin;

import com.gentle.bean.po.Note;
import com.gentle.exception.CheckException;
import com.gentle.result.ResultBean;
import com.gentle.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Gentle
 * @date 2019/09/18 : 18:39
 */
@RestController
@RequestMapping(value = "/admin/")
public class AdminNoteController {

//    @Autowired
//    NoteService noteService;
//
//    @PostMapping()
//    public ResultBean<String> delete( ) {
//
//        return new ResultBean<>();
//    }
//    @PostMapping()
//    public ResultBean<String> update( ) {
//
//        return new ResultBean<>();
//    }
//
//    @GetMapping()
//    public ResultBean<String> select() {
//
//        return new ResultBean<>();
//    }



}
