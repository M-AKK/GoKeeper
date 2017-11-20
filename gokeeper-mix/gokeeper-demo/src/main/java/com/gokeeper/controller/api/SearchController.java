package com.gokeeper.controller.api;

import com.gokeeper.service.SearchService;
import com.gokeeper.utils.ResultVoUtil;
import com.gokeeper.vo.JoinPreVo;
import com.gokeeper.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description: 条件搜索
 * @author: Created by Akk_Mac
 * @Date: 2017/11/20 17:15
 */
@RestController
@RequestMapping(value = "/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping
    public ResultVO search(@RequestParam(value = "ttpType") Integer ttpType,
                           @RequestParam(value = "minMoney", required = false) String minMoney,
                           @RequestParam(value = "maxMoney", required = false) String maxMoney,
                           @RequestParam(value = "startTime", required = false) String startTime,
                           @RequestParam(value = "finishTime", required = false) String finishTime
                           ) {

        List<JoinPreVo> result = searchService.highSearch(ttpType, minMoney, maxMoney, startTime, finishTime);

        return ResultVoUtil.success(result);
    }
}
