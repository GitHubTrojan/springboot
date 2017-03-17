package com.springboot.base;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Vincent on 2017/3/17. 2017-03-17 15:02:50
 * Description this is a base controller which has defined some common public methods to handle with requests / exceptions
 * SpringMVC/Boot 的 Controller 基类，处理异常和公共方法
 * @author Vincent
 * @version 1.0.0
 */
public abstract class BaseController<T> {
    /** 日志管理器 */
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    protected BaseService<T> baseService;

    /**
     * 功能说明：通用列表查询方法，使用pager.ftl的处理页面分页
     * @param request               请求对象
     * @param model                 数据对象
     * @param item                  查询条件
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    protected Object list(HttpServletRequest request, Model model, @ModelAttribute("uname") T item){
//        String pageNumObj = request.getParameter("page.index");
//        String pageSizeObj = request.getParameter("page.pageSize");
//        String pageTotal = request.getParameter("page.total");
        String pageNumObj = "0";
        String pageSizeObj = "3";
        String pageTotal = "10";
        PageBean page = new PageBean();
        if(StringUtils.isNotEmpty(pageNumObj)){
            page.setPageNum(Integer.parseInt((pageNumObj)));
        }
        if(StringUtils.isNotEmpty(pageSizeObj)){
            page.setPageSize(Integer.parseInt(pageSizeObj));
        }
        if(StringUtils.isNotEmpty(pageTotal)){
            page.setTotal(Integer.parseInt(pageTotal));
        }
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List list = baseService.queryForList(item);
        page.setTotal(((Page) list).getTotal());
        model.addAttribute("list", list);
        model.addAttribute("page", page);
        return model;
    }

    /**
     * 功能说明：通用跳转到新增页面
     * @return
     */
    @RequestMapping(value="add",method = RequestMethod.GET)
    protected String add() {
        return "添加的视图";
    }
    /**
     * 功能说明：通用跳转到编辑页面
     * @param model
     * @param id
     * @return view
     */
    @RequestMapping(value="/edit/{id}")
    public String edit(Model model,@PathVariable("id") String id){
//        model.addAttribute("item", getBaseService().findById(id));
        return "编辑页面";
    }
    @RequestMapping(value="/view/{id}")
    public String view(Model model,@PathVariable("id") String id){
//        model.addAttribute("item", getBaseService().findById(id));
        return "查看页面";
    }
    /**
     * 功能说明：通用保存页面
     * @param response
     * @param item
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @ResponseBody
    public void save(HttpServletResponse response,@ModelAttribute T item){
        Map<String,String> map = new HashMap<String, String>();
        try {
//            if(StringUtils.isNotEmpty(item.getId())){
//                getBaseService().update(item);
//            }else{
//                getBaseService().save(item);
//            }
            map.put("flag", "succ");
            map.put("msg", "保存成功！");
//            map.put("id", item.getId());
        } catch (Exception e) {
            map.put("flag", "error");
            map.put("msg", "保存失败！");
            e.printStackTrace();
        }

//        SpringMvcUtil.responseJSONWriter(response, map);
    }
    /**
     * 功能说明：通用删除功能
     * @param response          HttpServletResponse
     * @param id                删除的id
     */
    @RequestMapping(value="/del/{id}")
    public void del(HttpServletResponse response,@PathVariable String id){
        Map<String,String> map=new HashMap<String, String>();
        try {
            if(StringUtils.isNotEmpty(id)){
//                getBaseService().deleteById(id);
                map.put("flag", "succ");
                map.put("msg", "删除成功！");
            }else{
                map.put("flag", "error");
                map.put("msg", "请不要恶意操作！");
            }
        } catch (Exception e) {
            map.put("flag", "error");
            map.put("msg", "删除失败！");
            e.printStackTrace();
        }
//        SpringMvcUtil.responseJSONWriter(response, map);
    }
    /**
     * 功能说明：使用pager.ftl的处理页面分页
     *   直接返回springmvc的model
     * @return Model
     */
    protected Model executePage(Model model,T t){
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        String pageNumObj = request.getParameter("page.pageNum");
        String pageSizeObj = request.getParameter("page.pageSize");
        String pageTotal=request.getParameter("page.total");
        PageBean page=new PageBean();
        if(StringUtils.isNotEmpty(pageNumObj)){
            page.setPageNum(Integer.parseInt((pageNumObj)));
        }
        if(StringUtils.isNotEmpty(pageSizeObj)){
            page.setPageSize(Integer.parseInt(pageSizeObj));
        }
        if(StringUtils.isNotEmpty(pageTotal)){
            page.setTotal(Integer.parseInt(pageTotal));
        }
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List list = new ArrayList();
//                getBaseService().findListBy(t);
        page.setTotal(((Page) list).getTotal());
        model.addAttribute("list", list);
        model.addAttribute("page",page);
        return model;
    }
    /**
     * 功能说明：获取datatable传过来的数据,普通的get方法
     * @param t         查询的实体，需要的查询的值，通过实体传过来，由mybatis解析
     * @param prefix    排序字段的前缀
     * @return          封装datatable 需要的map数据，由@ResponseBody 转成对应的json
     */
    protected Map<String,Object> ReturnDataTableGet(T t,String prefix){
        Map<String,Object> map=new HashMap<String, Object>();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String sEcho = request.getParameter("sEcho"); // 点击的次数
        String startNum = request.getParameter("iDisplayStart"); //从第几条数据开始检索
        String pageSizeStr = request.getParameter("iDisplayLength");//显示的行数
        String sortIndex=request.getParameter("iSortCol_0");//排序索引
        String search=request.getParameter("sSearch");//搜索值
//        t.setSearchValue(search);
        String sort=request.getParameter("mDataProp_"+sortIndex);
        String dir=request.getParameter("sSortDir_0");//排序方式
        PageBean page = new PageBean();
        if(StringUtils.isNotEmpty(pageSizeStr)){
            page.setPageSize(Integer.parseInt(pageSizeStr));
        }
        if(StringUtils.isNotEmpty(startNum)){
            page.setPageNum((Integer.parseInt(startNum)/page.getPageSize())+1);
        }

        if(StringUtils.isEmpty(sort)){
            sort="id";
        }
        if(StringUtils.isEmpty(dir)){
            dir="asc";
        }
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List list = new ArrayList();
//                getBaseService().findListBy(t, sort, dir);
        page.setTotal(((Page) list).getTotal());
        map.put("sEcho", sEcho);
        map.put("iTotalRecords", page.getTotal());
        map.put("iTotalDisplayRecords", page.getTotal());
        map.put("aaData", list);
        return map;
    }
    /**
     * 功能说明：dataTable ajax post 请求数据的封装
     * @param t             查询的实体，需要的查询的值，通过实体传过来，由mybatis解析
     * @param prefix        排序字段的前缀
     * @return              封装datatable 需要的map数据，由@ResponseBody 转成对应的json
     */
    protected Map<String,Object> ReturnDataTableAjaxPost(T t,String prefix){
        Map<String,Object> map=new HashMap<String, Object>();
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        String sEcho = request.getParameter("draw"); // 点击的次数
        String startNum = request.getParameter("start"); //从第几条数据开始检索
        String pageSizeStr = request.getParameter("length");//显示的行数
        String sortIndex=request.getParameter("order[0][column]");//排序索引
        String search=request.getParameter("search[value]");//搜索值
//        t.setSearchValue(search);
        String sort=request.getParameter("columns["+sortIndex+"][data]");
        String dir=request.getParameter("order[0][dir]");//排序方式
        PageBean page = new PageBean();
        if(StringUtils.isNotEmpty(pageSizeStr)){
            page.setPageSize(Integer.parseInt(pageSizeStr));
        }
        if(StringUtils.isNotEmpty(startNum)){
            page.setPageNum((Integer.parseInt(startNum)/page.getPageSize())+1);
        }

        if(StringUtils.isEmpty(sort)){
            sort="id";
        }
        if(StringUtils.isEmpty(dir)){
            dir="asc";
        }
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List list = new ArrayList();
//                getBaseService().findListBy(t, sort, dir);
        page.setTotal(((Page) list).getTotal());
        map.put("draw", sEcho);
        map.put("length",pageSizeStr);
        map.put("iTotalRecords", page.getTotal());
        map.put("iTotalDisplayRecords", page.getTotal());
        map.put("data", list);
        return map;
    }
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
    @ExceptionHandler
    public String exception(HttpServletRequest request, HttpServletResponse response, Exception e) {
        logger.error(this.getClass()+" is errory, errorType="+e.getClass(),e);
        //如果是json格式的ajax请求
        if (request.getHeader("accept").indexOf("application/json") > -1
                || (request.getHeader("X-Requested-With")!= null && request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1)) {
            response.setStatus(500);
            response.setContentType("application/json;charset=utf-8");
//            SpringMvcUtil.responseWriter(response, e.getMessage());
            return null;
        }
        else{//如果是普通请求
            request.setAttribute("exceptionMsg", e.getMessage());
            // 根据不同的异常类型可以返回不同界面
            if(e instanceof SQLException)
                return "sqlerror";
            else
                return "error";
        }
    }

    private static final ThreadLocal<HttpServletRequest> requestContainer = new ThreadLocal<HttpServletRequest>();

    private static final ThreadLocal<HttpServletResponse> responseContainer = new ThreadLocal<HttpServletResponse>();

    private static final ThreadLocal<ModelMap> modelContainer = new ThreadLocal<ModelMap>();

    /**
     * 初始化response
     *
     * @param response
     */
    @ModelAttribute
    private final void initResponse(HttpServletResponse response) {
        responseContainer.set(response);
    }

    /**
     * 获取当前线程的response对象
     *
     * @return
     */
    protected final HttpServletResponse getResponse() {
        return responseContainer.get();
    }

    /**
     * 初始化request
     *
     * @param request
     */
    @ModelAttribute
    private final void initRequest(HttpServletRequest request) {
        requestContainer.set(request);
    }

    /**
     * 获取当前线程的request对象
     *
     * @return
     */
    protected final HttpServletRequest getRequest() {
        return requestContainer.get();
    }

    /**
     * 设置model
     *
     * @param model
     */
    @ModelAttribute
    private final void initModelMap(ModelMap model) {
        modelContainer.set(model);
    }

    /**
     * 获取当前线程的modelMap对象
     *
     * @return
     */
    protected final ModelMap getModelMap() {
        return modelContainer.get();
    }
}
