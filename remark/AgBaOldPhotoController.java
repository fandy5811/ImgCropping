package cn.com.taiji.bjyl.agbaoldphoto.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import cn.com.taiji.bjyl.agbaoldphoto.entity.AgBaOldPhoto;
import cn.com.taiji.bjyl.agbaoldphoto.service.AgBaOldPhotoService;
import cn.com.taiji.component.gos.entity.User;
import cn.com.taiji.component.gos.session.UserSession;
import cn.com.taiji.framework.rest.security.EncryptIgnore;
import cn.com.taiji.manage.bafile.entity.BaLxbtFile;

@RestController
@RequestMapping("/{appid}")
public class AgBaOldPhotoController {
	
	@Resource
	private  AgBaOldPhotoService agBaOldPhotoService;
	
	/**
	* 保存老人信息及老人照片
	 */
	@RequestMapping(path = "/ctrl/{pojoName}/saveAgBaOldPhoto", method = RequestMethod.POST)
	public void saveAgBaOldBase(HttpServletRequest request,
			@RequestParam("agBaOldBaseId")String agBaOldBaseId,@RequestParam("srcValue")String srcValue) throws IOException {
		
		MultipartFile file = null;
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		if(multipartResolver.isMultipart(request)){
		    MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  
		    file = multiRequest.getFileMap().get("file");
		}
		
		//附件
		AgBaOldPhoto agBaOldPhoto = null;
		
		List list = agBaOldPhotoService.findAgBaOldPhoto(Long.valueOf(agBaOldBaseId));
		//说明是修改
		if(list != null && list.size() > 0){
			agBaOldPhoto = (AgBaOldPhoto)list.get(0);
		}else{//新增
			agBaOldPhoto = new AgBaOldPhoto();
		}
		
		if(file != null && !file.isEmpty()){
			try {
				agBaOldPhoto.setAgBaOldBaseId(Long.valueOf(agBaOldBaseId));
                agBaOldPhoto.setPhotoName(file.getOriginalFilename());
                agBaOldPhoto.setPhotoSize(file.getSize()/1024);
                agBaOldPhoto.setSrcValue(srcValue);
                agBaOldPhoto.setZzPhotoContent(file.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		agBaOldPhotoService.save(agBaOldPhoto);
		
	}

}