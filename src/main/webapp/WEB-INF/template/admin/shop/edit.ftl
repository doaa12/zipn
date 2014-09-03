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

	// 表单验证
	$inputForm.validate({
		rules: {
			shopId: "required",
			adminId: {
				min: 1
			}
		}
	});

});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.attribute.add")}
	</div>
	<form id="inputForm" action="update.jhtml" method="post">
		<input type="hidden" name="shopId" value="${shop.id}">
		<table id="shopTable" class="input">
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Shop.productCategory")}:
				</th>
				<td>
					<p>${shop.productCategory.name}</p>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Shop.name")}:
				</th>
				<td>
					<p>${shop.name}</p>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Shop.city")}:
				</th>
				<td>
					<p>${shop.city}</p>
				</td>
			</tr>
			<tr>
				<th>
					${message("admin.common.setting")}:
				</th>
				<td>
					<label>
						<input type="checkbox" name="isList" value="true"/ [#if shop.isList] checked="checked"[/#if]>${message("Shop.isList")}
						<input type="hidden" name="_isList" value="false" />
					</label>
					<label>
						<input type="checkbox" name="isTop" value="true" [#if shop.isTop] checked="checked"[/#if]/>${message("Shop.isTop")}
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
						[#list admins as admin]
							<option value="${admin.id}"[#if shop.admin == admin] selected="selected"[/#if]>
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