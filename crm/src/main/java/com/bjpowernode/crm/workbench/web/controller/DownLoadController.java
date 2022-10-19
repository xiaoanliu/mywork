package com.bjpowernode.crm.workbench.web.controller;

import com.alibaba.druid.pool.vendor.SybaseExceptionSorter;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * @author: Liuag
 * @Project: JavaTraining
 * @Pcakage: com.bjpowernode.crm.workbench.web.controller.DownLoadController
 * @Date: 2022年10月09日 20:33
 * @Description:
 */
public class DownLoadController extends HttpServlet {

//    @Override
//    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("=========doget");
        //获取需要下载的文件名
        //String realPath = this.getServletContext().getRealPath("/WEB-INF/classes/1.docx");
        //或者直接写绝对路径
        String realPath="F:\\workspace\\workspaceForCrm\\crm\\src\\main\\resources\\1.docx";
        //2.获取要下载的文件名
        String fileName = realPath.substring(realPath.lastIndexOf("\\") + 1);
        //3.设置content-disposition响应头控制浏览器以下载的形式打开文件
        //下载中文文件时，需要注意的地方就是中文文件名要使用
        // URLEncoder.encode方法进行编码(URLEncoder.encode(fileName, "字符编码"))，否则会出现文件名乱码。
        resp.setHeader("content-disposition","attachment;filename="+ URLEncoder.encode(fileName,"UTF-8"));
        //4.获取要下载的文件输入流
        ServletInputStream inputStream = req.getInputStream();
        //5.准备数据缓冲区
        int len=0;
        byte[] buffer=new byte[1024];
        //6.通过response对象获取OutputStream流
        ServletOutputStream outputStream = resp.getOutputStream();
        //7.将FileInputStream流写入到buffer缓冲区
        while ((len=inputStream.read(buffer))!=-1){
            //8.使用OutputStream将缓冲区的数据输出到客户端浏览器
            outputStream.write(buffer,0,len);
        }
        //关闭流
        outputStream.close();
        inputStream.close();
        System.out.println("下载结束");

    }
}
