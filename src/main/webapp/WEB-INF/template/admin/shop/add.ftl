<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.attribute.add")} </title>
<meta name="author" content="Sunry" />
<meta name="copyright" content="" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
	var $provinceId = $("#provinceId");
	var cityId = $("#cityId")
	
	// 修改店铺省份
	$provinceId.change(function(){
		$.ajax({
			url: "cities.jhtml",
			type: "GET",
			data: {id: $provinceId.val()},
			dataType: "json",
			beforeSend: function() {
				$cityId.empty();
			},
			success: function(data) {
				var trHtml = '<option value="0">请选择...</option>';
				$.each(data, function(i, city) {
					trHtml += '<option value="' + city.id + '">' + city.name + '</option>' ;
				});
				$cityId.append(trHtml);
			}
		});
	});
	
	// 表单验证
	$inputForm.validate({
		rules: {
			productCategoryId: "required",
			name: "required",
			adminId: "required",
			provinceId: "required"
		}
	});

});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.attribute.add")}
	</div>
	<form id="inputForm" action="save.jhtml" method="post">
		<table id="shopTable" class="input">
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Shop.productCategory")}:
				</th>
				<td>
					<select id="productCategoryId" name="productCategoryId">
						[#list productCategoryTree as productCategory]
							<option value="${productCategory.id}">
								[#if productCategory.grade != 0]
									[#list 1..productCategory.grade as i]
										&nbsp;&nbsp;
									[/#list]
								[/#if]
								${productCategory.name}
							</option>
						[/#list]
					</select>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Shop.name")}:
				</th>
				<td>
					<input type="text" id="name" name="name" class="text" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Shop.city")}:
				</th>
				<td>
					<select id="provinceId" name="provinceId">
						[#list provinces as province]
							<option value="${province.id}">
								${province.name}
							</option>
						[/#list]
					</select>
					<select id="cityId" name="cityId">
						<option value="0">请选择...</option>
						[#list cities as city]
							<option value="${city.id}">
								${city.name}
							</option>
						[/#list]
					</select>
				</td>
			</tr>
			<tr>
				<th>
					${message("admin.common.setting")}:
				</th>
				<td>
					<label>
						<input type="checkbox" name="isList" value="true"/>${message("Shop.isList")}
						<input type="hidden" name="_isList" value="false" />
					</label>
					<label>
						<input type="checkbox" name="isTop" value="true"/>${message("Shop.isTop")}
						<input type="hidden" name="_isTop" value="false" />
					</label>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Shop.admin")}:
				</th>
				<td>
					<select id="adminId" name="adminId">
						<option value="0">请选择...</option>
						[#list admins as admin]
							<option value="${admin.id}">
								${admin.username}
							</option>
						[/#list]
					</select>
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