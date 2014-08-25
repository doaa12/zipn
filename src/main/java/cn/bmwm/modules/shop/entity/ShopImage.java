/**
 * 
 */
package cn.bmwm.modules.shop.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Transient;
import javax.validation.constraints.Min;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zhoupuyue
 * @date 2014-8-25
 */
public class ShopImage implements Serializable,Comparable<ShopImage> {

	private static final long serialVersionUID = 3227265809145760156L;
	
	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 图片路径
	 */
	private String path;
	
	/**
	 * 排序
	 */
	private Integer order;
	
	/**
	 * 文件
	 */
	private MultipartFile file;


	/**
	 * @return the title
	 */
	@Length(max = 200)
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the order
	 */
	@Min(0)
	@Column(name = "orders")
	public Integer getOrder() {
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(Integer order) {
		this.order = order;
	}

	/**
	 * @return the file
	 */
	@Transient
	public MultipartFile getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	/**
	 * 判断是否为空
	 * 
	 * @return 是否为空
	 */
	@Transient
	public boolean isEmpty() {
		return (getFile() == null || getFile().isEmpty()) && (StringUtils.isEmpty(getPath()));
	}
	
	/**
	 * 实现compareTo方法
	 */
	@Override
	public int compareTo(ShopImage shopImage) {
		return new CompareToBuilder().append(getOrder(), shopImage.getOrder()).toComparison();
	}
	
}
