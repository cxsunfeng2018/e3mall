package cn.e3.manager.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.e3.utils.FastDFSClient;
import cn.e3.utils.PicResult;

@Controller
public class UploadController {

	@Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SERVER_URL;

	@RequestMapping("/pic/upload")
	@ResponseBody
	public PicResult uploadPic(MultipartFile uploadFile) {
		try {
			// 获取文件名称
			String filename = uploadFile.getOriginalFilename();
			// 获取文件扩展名
			String extName = filename.substring(filename.lastIndexOf(".") + 1);
			// 创建图片服务器上传工具类
			FastDFSClient fClient = new FastDFSClient("classpath:client.conf");
			String url = fClient.uploadFile(uploadFile.getBytes(), extName);
			// 组合url图片服务器完整路径
			url = "http://192.168.66.67/" + url;
			// 创建一个PicResult对象，设置返回值结果
			PicResult pic = new PicResult();
			pic.setError(0);
			pic.setUrl(url);
			return pic;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// 上传图片失败
			// 创建一个PicResult对象，设置返回值结果
			PicResult pic = new PicResult();
			pic.setError(1);
			pic.setMessage("上传失败");
			return pic;
		}
	}

}
