<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.productCategory.add")} </title>
<meta name="author" content="Sunry" />
<meta name="copyright" content="" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<style type="text/css">
.brands label {
	width: 150px;
	display: block;
	float: left;
	padding-right: 6px;
}
</style>
<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
	var $provinceId = $("#provinceId");
	var $cityId = $("#cityId");
	var $areaId = $("#areaId");
	
	[@flash_message /]

	// 表单验证
	$inputForm.validate({
		rules: {
			name: "required",
			order: "digits"
		}
	});
	
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
	
});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.virtualShopCategory.add")}
	</div>
	<form id="inputForm" action="save.jhtml" method="post">
		<table class="input">
			<tr>
				<th>
					<span class="requiredField">*</span>${message("VirtualShopCategory.name")}:
				</th>
				<td>
					<input type="text" id="name" name="name" class="text" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("VirtualShopCategory.city")}:
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
					${message("admin.common.order")}:
				</th>
				<td>
					<input type="text" name="order" class="text" maxlength="9" />
				</td>
			</tr>
			<tr>
				<th>
					&nbsp;
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