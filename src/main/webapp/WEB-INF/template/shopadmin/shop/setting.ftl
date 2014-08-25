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

	$addShopImage = $("#addShopImage");

	// 表单验证
	$inputForm.validate({
		rules: {
			shopId: "required",
			adminId: "required"
		}
	});
	
	// 增加店铺图片
	$addShopImage.click(function() {
		[@compress single_line = true]
			var trHtml = 
			'<tr>
				<td>
					<input type="file" name="productImages[' + productImageIndex + '].file" class="productImageFile" \/>
				<\/td>
				<td>
					<input type="text" name="productImages[' + productImageIndex + '].title" class="text" maxlength="200" \/>
				<\/td>
				<td>
					<input type="text" name="productImages[' + productImageIndex + '].order" class="text productImageOrder" maxlength="9" style="width: 50px;" \/>
				<\/td>
				<td>
					<a href="javascript:;" class="deleteProductImage">[${message("admin.common.delete")}]<\/a>
				<\/td>
			<\/tr>';
		[/@compress]
		$productImageTable.append(trHtml);
		productImageIndex ++;
	});

});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.attribute.add")}
	</div>
	<form id="inputForm" action="update.jhtml" method="post">
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
					<input type="text" id="name" name="name" class="text" value="${shop.name}" maxlength="200" />
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
					<span class="requiredField">*</span>${message("shopadmin.shop.address")}:
				</th>
				<td>
					<input type="text" id="address" name="address" value="${shop.address}" class="text" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("shopadmin.shop.description")}:
				</th>
				<td>
					<input type="text" id="description" name="description" value="${shop.description}" class="text" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("shopadmin.shop.payaccount")}:
				</th>
				<td>
					<input type="text" id="payAccount" name="payAccount" value="${shop.payAccount}" class="text" maxlength="50" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("shopadmin.shop.status")}:
				</th>
				<td>
					<select id="status" name="status">
						<option value="1" [#if shop.status == 1] selected="selected"[/#if]>空闲</option>
						<option value="2" [#if shop.status == 2] selected="selected"[/#if]>忙碌</option>
						<option value="3" [#if shop.status == 3] selected="selected"[/#if]>火爆</option>
					</select>
				</td>
			</tr>
		</table>
		<table id="shopImageTable" class="input tabContent">
			<tr>
				<td colspan="4">
					<a href="javascript:;" id="addShopImage" class="button">${message("shopadmin.shop.addShopImage")}</a>
				</td>
			</tr>
			<tr class="title">
				<td>
					${message("ShopImage.file")}
				</td>
				<td>
					${message("ShopImage.title")}
				</td>
				<td>
					${message("admin.common.order")}
				</td>
				<td>
					${message("admin.common.delete")}
				</td>
			</tr>
			[#list shop.shopImages as shopImage]
				<tr>
					<td>
						<input type="file" name="shopImages[${shopImage_index}].file" class="productImageFile ignore" />
						<a href="${shopImage.path}" target="_blank">${message("admin.common.view")}</a>
					</td>
					<td>
						<input type="text" name="shopImages[${shopImage_index}].title" class="text" maxlength="200" value="${shopImage.title}" />
					</td>
					<td>
						<input type="text" name="productImages[${shopImage_index}].order" class="text productImageOrder" value="${shopImage.order}" maxlength="9" style="width: 50px;" />
					</td>
					<td>
						<a href="javascript:;" class="deleteProductImage">[${message("admin.common.delete")}]</a>
					</td>
				</tr>
			[/#list]
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