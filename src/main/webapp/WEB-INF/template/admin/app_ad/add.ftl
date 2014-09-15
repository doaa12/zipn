<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.attribute.add")} </title>
<meta name="author" content="Sunry" />
<meta name="copyright" content="" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
	var $provinceId = $("#provinceId");
	var $cityId = $("#cityId");
	var $areaId = $("#areaId");
	var $browserButton = $("#browserButton");
	
	[@flash_message /]
	
	$browserButton.browser();
	
	//修改省份
	$provinceId.change(function(){
		$.ajax({
			url: "/admin/area/children.jhtml",
			type: "GET",
			data: {id: $provinceId.val()},
			dataType: "json",
			beforeSend: function() {
				$cityId.empty();
			},
			success: function(data) {
			
				var html = '<option value="0">请选择...</option>';
				$.each(data, function(i, city) {
					html += '<option value="' + city.id + '">' + city.name + '</option>' ;
				});
				$cityId.append(html);
				
				var ahtml = '<option value="0">请选择...</option>';
				$areaId.empty();
				$areaId.append(ahtml);
				
			}
		});
	});
	
	//修改城市
	$cityId.change(function(){
		$.ajax({
			url: "/admin/area/children.jhtml",
			type: "GET",
			data: {id: $cityId.val()},
			dataType: "json",
			success: function(data) {
				if(!data || data.length == 0){
					$areaId.empty();
					$areaId.css('display', 'none');
					return;
				}
				var html = '<option value="0">请选择...</option>';
				$.each(data, function(i, area) {
					html += '<option value="' + area.id + '">' + area.name + '</option>' ;
				});
				$areaId.empty();
				$areaId.append(html);
				$areaId.css('display', 'block');
			}
		});
	});
	
	// 表单验证
	$inputForm.validate({
		rules: {
			imageUrl: "required",
			linkUrl: "required",
			provinceId: "required",
			order: "required"
		}
	});

});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.appAdvertise.add")}
	</div>
	<form id="inputForm" action="save.jhtml" method="post">
		<table id="shopTable" class="input">
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Shop.city")}:
				</th>
				<td>
					<select id="provinceId" name="provinceId">
						<option value="0">请选择...</option>
						[#list provinces as province]
							<option value="${province.id}">
								${province.name}
							</option>
						[/#list]
					</select>
					<select id="cityId" name="cityId">
						<option value="0">请选择...</option>
					</select>
					<select id="areaId" name="areaId" style="display:none;">
					</select>
				</td>
			</tr>
			<tr>
				<th>
					${message("admin.common.setting")}:
				</th>
				<td>
					<label>
						<input type="checkbox" name="isEnabled" value="true"/>${message("admin.appAdvertise.isEnabled")}
						<input type="hidden" name="_isEnabled" value="false" />
					</label>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("AppAdvertise.imageurl")}:
				</th>
				<td>
					<span class="fieldSet">
						<input type="text" id="imageUrl" name="imageUrl" class="text_medium" maxlength="200" title="${message("admin.appAdvertise.imageTitle")}" />
						<input type="button" id="browserButton" class="button" value="${message("admin.browser.select")}" />
					</span>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("admin.appAdvertise.linkurl")}:
				</th>
				<td>
					<input type="text" id="linkUrl" name="linkUrl" class="text_medium" maxlength="50" title="${message("admin.appAdvertise.linkurlTitle")}"/>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("admin.appAdvertise.order")}:
				</th>
				<td>
					<input type="text" id="order" name="order" class="text_medium" maxlength="50" title="${message("admin.appAdvertise.orderTitle")}"/>
				</td>
			</tr>
		</table>
		<table class="input">
			<tr>
				<th>&nbsp;
					
				</th>
				<td>
					<input type="submit" class="button" value="${message("admin.common.submit")}" />
					<input type="button" id="backButton" class="button" value="${message("admin.common.back")}" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>