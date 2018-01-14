package com.albert.pojo;

import java.util.Set;


import net.sf.jasperreports.engine.util.DigestUtils;

/** 
* @ClassName: ConfigEntity 
* @Description: 
* @author albert
* @date 2018年1月10日 下午4:31:24 
*  
*/
public class ConfigEntity {
	
	private Set<JasperForPrinter> jasperPrinters;
	
	private String url;

	public ConfigEntity() {
	}
	public ConfigEntity(String url,Set<JasperForPrinter> jasperForPrinters){
		this.url=url;
		this.jasperPrinters= jasperForPrinters;
	}
	public JasperForPrinter genPrinter(String jasper, String printer, Integer review){
		return new JasperForPrinter(jasper, printer, review);
	}
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	public Set<JasperForPrinter> getJasperPrinters() {
		return jasperPrinters;
	}

	public void setJasperPrinters(Set<JasperForPrinter> jasperPrinters) {
		this.jasperPrinters = jasperPrinters;
	}
	public class JasperForPrinter{
		private String uuid;
		private String jasper;
		private String printer;
		private Integer review;
		private Boolean status;
		
		public JasperForPrinter() {
		}
		
		public JasperForPrinter(String jasper, String printer, Integer review) {
			this.jasper = jasper;
			this.printer = printer;
			this.review = review;
			this.uuid =DigestUtils.instance().md5(jasper.trim()).toString();
			
		}

		public String getUuid() {
			return uuid;
		}
		public void setUuid(String uuid) {
			this.uuid = uuid;
		}
		public String getJasper() {
			return jasper;
		}
		public void setJasper(String jasper) {
			this.jasper = jasper;
		}
		public String getPrinter() {
			return printer;
		}
		public void setPrinter(String printer) {
			this.printer = printer;
		}
		public Integer getReview() {
			return review;
		}
		public void setReview(Integer review) {
			this.review = review;
		}
		public Boolean getStatus() {
			return status;
		}
		public void setStatus(Boolean status) {
			this.status = status;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof JasperForPrinter)) {
				return false;
			}
			JasperForPrinter other = (JasperForPrinter) obj;
			if (uuid == null) {
				if (other.uuid != null) {
					return false;
				}
			} else if (!uuid.equals(other.uuid)) {
				return false;
			}
			return true;
		}
	}
	
}
