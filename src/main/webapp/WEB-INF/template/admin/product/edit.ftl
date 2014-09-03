<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.product.edit")} </title>
<meta name="author" content="Sunry" />
<meta name="copyright" content="" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/editor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<style type="text/css">
	.specificationSelect {
		height: 100px;
		padding: 5px;
		overflow-y: scroll;
		border: 1px solid #cccccc;
	}
	
	.specificationSelect li {
		float: left;
		min-width: 150px;
		_width: 200px;
	}
</style>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.product.edit")}
	</div>
	<form id="inputForm" action="update.jhtml" method="post" enctype="multipart/form-data">
		<input type="hidden" name="id" value="${product.id}" />
		<ul id="tab" class="tab">
			<li>
				<input type="button" value="${message("admin.product.base")}" />
			</li>
			<li>
				<input type="button" value="${message("admin.product.introduction")}" />
			</li>
			<li>
				<input type="button" value="${message("admin.product.productImage")}" />
			</li>
			<li>
				<input type="button" value="${message("admin.product.parameter")}" />
			</li>
			<li>
				<input type="button" value="${message("admin.product.attribute")}" />
			</li>
			<li>
				<input type="button" value="${message("admin.product.specification")}" />
			</li>
		</ul>
		<table class="input tabContent">
			[#if product.specifications?has_content]
				<tr>
					<th>
						${message("Product.specifications")}:
					</th>
					<td>
						[#list product.specificationValues as specificationValue]
							${specificationValue.name}
						[/#list]
					</td>
				</tr>
			[/#if]
			[#if product.validPromotions?has_content]
				<tr>
					<th>
						${message("Product.promotions")}:
					</th>
					<td>
						[#list product.validPromotions as promotion]
							<p>
								${promotion.name}
								[#if promotion.beginDate?? || promotion.endDate??]
									[${promotion.beginDate} ~ ${promotion.endDate}]
								[/#if]
							</p>
						[/#list]
					</td>
				</tr>
			[/#if]
			<tr>
				<th>
					${message("Product.productCategory")}:
				</th>
				<td>
					${product.shop.productCategory.name}
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Product.name")}:
				</th>
				<td>
					<input type="text" name="name" class="text" value="${product.name}" maxlength="200" readonly/>
				</td>
			</tr>
			<tr>
				<th>
					${message("Product.sn")}:
				</th>
				<td>
					<input type="text" name="sn" class="text" value="${product.sn}" maxlength="100" title="${message("admin.product.snTitle")}" readonly/>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Product.price")}:
				</th>
				<td>
					<input type="text" name="price" class="text" value="${product.price}" maxlength="16" readonly/>
				</td>
			</tr>
			<tr>
				<th>
					${message("Product.memberPrice")}:
				</th>
				<td>
					<label>
						<input type="checkbox" id="isMemberPrice" name="isMemberPrice" value="true"[#if product.memberPrice?has_content] checked="checked"[/#if] disabled="disabled"/>${message("admin.product.isMemberPrice")}
					</label>
				</td>
			</tr>
			<tr id="memberPriceTr"[#if !product.memberPrice?has_content] class="hidden"[/#if]>
				<th>
					&nbsp;
				</th>
				<td>
					[#list memberRanks as memberRank]
						${memberRank.name}: <input type="text" name="memberPrice_${memberRank.id}" class="text memberPrice" value="${product.memberPrice.get(memberRank)}" maxlength="16" style="width: 60px; margin-right: 6px;"[#if !product.memberPrice?has_content] disabled="disabled"[/#if] readonly/>
					[/#list]
				</td>
			</tr>
			<tr>
				<th>
					${message("Product.cost")}:
				</th>
				<td>
					<input type="text" name="cost" class="text" value="${product.cost}" maxlength="16" title="${message("admin.product.costTitle")}" readonly/>
				</td>
			</tr>
			<tr>
				<th>
					${message("Product.marketPrice")}:
				</th>
				<td>
					<input type="text" name="marketPrice" class="text" value="${product.marketPrice}" maxlength="16" title="${message("admin.product.marketPriceTitle")}" readonly/>
				</td>
			</tr>
			<tr>
				<th>
					${message("Product.image")}:
				</th>
				<td>
					<span class="fieldSet">
						<input type="text" name="image" class="text" value="${product.image}" maxlength="200" title="${message("admin.product.imageTitle")}" />
						<input type="button" id="browserButton" class="button" value="${message("admin.browser.select")}" />
						[#if product.image??]
							<a href="${product.image}" target="_blank">${message("admin.common.view")}</a>
						[/#if]
					</span>
				</td>
			</tr>
			<tr>
				<th>
					${message("Product.unit")}:
				</th>
				<td>
					<input type="text" name="unit" class="text" maxlength="200" value="${product.unit}" readonly/>
				</td>
			</tr>
			<tr>
				<th>
					${message("Product.weight")}:
				</th>
				<td>
					<input type="text" name="weight" class="text" value="${product.weight}" maxlength="9" title="${message("admin.product.weightTitle")}" readonly/>
				</td>
			</tr>
			<tr>
				<th>
					${message("Product.stock")}:
				</th>
				<td>
					<input type="text" name="stock" class="text" value="${product.stock}" maxlength="9" title="${message("admin.product.stockTitle")}" readonly/>
				</td>
			</tr>
			<tr>
				<th>
					${message("Product.stockMemo")}:
				</th>
				<td>
					<input type="text" name="stockMemo" class="text" value="${product.stockMemo}" maxlength="200" readonly/>
				</td>
			</tr>
			<tr>
				<th>
					${message("Product.point")}:
				</th>
				<td>
					<input type="text" name="point" class="text" value="${product.point}" maxlength="9" title="${message("admin.product.pointTitle")}" readonly/>
				</td>
			</tr>
			<tr>
				<th>
					${message("Product.brand")}:
				</th>
				<td>
					[#if product.brand??]
						${product.brand.name}
					[/#if]
				</td>
			</tr>
			<tr>
				<th>
					${message("Product.tags")}:
				</th>
				<td>
					[#list tags as tag]
						<label>
							<input type="checkbox" name="tagIds" value="${tag.id}"[#if product.tags?seq_contains(tag)] checked="checked"[/#if] disabled="disabled"/>${tag.name}
						</label>
					[/#list]
				</td>
			</tr>
			<tr>
				<th>
					${message("admin.common.setting")}:
				</th>
				<td>
					<label>
						<input type="checkbox" name="isMarketable" value="true"[#if product.isMarketable] checked="checked"[/#if] disabled="disabled" />${message("Product.isMarketable")}
						<input type="hidden" name="_isMarketable" value="false" />
					</label>
					<label>
						<input type="checkbox" name="isList" value="true"[#if product.isList] checked="checked"[/#if] disabled="disabled"/>${message("Product.isList")}
						<input type="hidden" name="_isList" value="false" />
					</label>
					<label>
						<input type="checkbox" name="isTop" value="true"[#if product.isTop] checked="checked"[/#if] />${message("Product.isTop")}
						<input type="hidden" name="_isTop" value="false" />
					</label>
					<label>
						<input type="checkbox" name="isGift" value="true"[#if product.isGift] checked="checked"[/#if] disabled="disabled"/>${message("Product.isGift")}
						<input type="hidden" name="_isGift" value="false" />
					</label>
				</td>
			</tr>
			<tr>
				<th>
					${message("Product.memo")}:
				</th>
				<td>
					<input type="text" name="memo" class="text" value="${product.memo}" maxlength="200" readonly/>
				</td>
			</tr>
			<tr>
				<th>
					${message("Product.keyword")}:
				</th>
				<td>
					<input type="text" name="keyword" class="text" value="${product.keyword}" maxlength="200" title="${message("admin.product.keywordTitle")}" readonly/>
				</td>
			</tr>
			<tr>
				<th>
					${message("Product.seoTitle")}:
				</th>
				<td>
					<input type="text" name="seoTitle" class="text" value="${product.seoTitle}" maxlength="200" readonly/>
				</td>
			</tr>
			<tr>
				<th>
					${message("Product.seoKeywords")}:
				</th>
				<td>
					<input type="text" name="seoKeywords" class="text" value="${product.seoKeywords}" maxlength="200" readonly/>
				</td>
			</tr>
			<tr>
				<th>
					${message("Product.seoDescription")}:
				</th>
				<td>
					<input type="text" name="seoDescription" class="text" value="${product.seoDescription}" maxlength="200" readonly/>
				</td>
			</tr>
		</table>
		<table class="input tabContent">
			<tr>
				<td>
					${product.introduction}
				</td>
			</tr>
		</table>
		<table id="productImageTable" class="input tabContent">
			<tr>
				<td colspan="4">
					<a href="javascript:;" id="addProductImage" class="button">${message("admin.product.addProductImage")}</a>
				</td>
			</tr>
			<tr class="title">
				<th>
					${message("ProductImage.file")}
				</th>
				<th>
					${message("ProductImage.title")}
				</th>
				<th>
					${message("admin.common.order")}
				</th>
				<th>
					${message("admin.common.delete")}
				</th>
			</tr>
			[#list product.productImages as productImage]
				<tr>
					<td>
						<input type="hidden" name="productImages[${productImage_index}].source" value="${productImage.source}" />
						<input type="hidden" name="productImages[${productImage_index}].large" value="${productImage.large}" />
						<input type="hidden" name="productImages[${productImage_index}].medium" value="${productImage.medium}" />
						<input type="hidden" name="productImages[${productImage_index}].thumbnail" value="${productImage.thumbnail}" />
						<input type="file" name="productImages[${productImage_index}].file" class="productImageFile ignore" />
						<a href="${productImage.large}" target="_blank">${message("admin.common.view")}</a>
					</td>
					<td>
						<input type="text" name="productImages[${productImage_index}].title" class="text" maxlength="200" value="${productImage.title}" />
					</td>
					<td>
						<input type="text" name="productImages[${productImage_index}].order" class="text productImageOrder" value="${productImage.order}" maxlength="9" style="width: 50px;" />
					</td>
					<td>
						<a href="javascript:;" class="deleteProductImage">[${message("admin.common.delete")}]</a>
					</td>
				</tr>
			[/#list]
		</table>
		<table id="parameterTable" class="input tabContent">
			[#list product.shopCategory.parameterGroups as parameterGroup]
				<tr>
					<td style="text-align: right; padding-right: 10px;">
						<strong>${parameterGroup.name}:</strong>
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
				[#list parameterGroup.parameters as parameter]
					<tr>
						<th>${parameter.name}:</th>
						<td>
							<input type="text" name="parameter_${parameter.id}" class="text" value="${product.parameterValue.get(parameter)}" maxlength="200" readonly/>
						</td>
					</tr>
				[/#list]
			[/#list]
		</table>
		<table id="attributeTable" class="input tabContent">
			[#list product.shopCategory.attributes as attribute]
				<tr>
					<th>${attribute.name}:</th>
					<td>
						<select name="attribute_${attribute.id}" disabled="disabled">
							<option value="">${message("admin.common.choose")}</option>
							[#list attribute.options as option]
								<option value="${option}"[#if option == product.getAttributeValue(attribute)] selected="selected"[/#if]>${option}</option>
							[/#list]
						</select>
					</td>
				</tr>
			[/#list]
		</table>
		<table class="input tabContent">
			<tr class="title">
				<th>
					${message("admin.product.selectSpecification")}:
				</th>
			</tr>
			<tr>
				<td>
					<div id="specificationSelect" class="specificationSelect">
						<ul>
							[#list specifications as specification]
								<li>
									<label>
										<input type="checkbox" name="specificationIds" value="${specification.id}"[#if product.specifications?seq_contains(specification)] checked="checked"[/#if] disabled="disabled"/>${specification.name}
										[#if specification.memo??]
											<span class="gray">[${specification.memo}]</span>
										[/#if]
									</label>
								</li>
							[/#list]
						</ul>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<a href="javascript:;" id="addSpecificationProduct" class="button">${message("admin.product.addSpecificationProduct")}</a>
				</td>
			</tr>
			<tr>
				<td>
					<table id="specificationProductTable" class="input">
						<tr class="title">
							<td width="60">
								&nbsp;
							</td>
							[#list specifications as specification]
								<td class="specification_${specification.id}[#if !product.specifications?seq_contains(specification)] hidden[/#if]">
									${specification.name}
									[#if specification.memo??]
										<span class="gray">[${specification.memo}]</span>
									[/#if]
								</td>
							[/#list]
							<td>
								${message("admin.common.handle")}
							</td>
						</tr>
						<tr class="hidden">
							<td>
								&nbsp;
							</td>
							[#list specifications as specification]
								<td class="specification_${specification.id}[#if !product.specifications?seq_contains(specification)] hidden[/#if]">
									<select name="specification_${specification.id}" disabled="disabled">
										[#list specification.specificationValues as specificationValue]
											<option value="${specificationValue.id}">${specificationValue.name}</option>
										[/#list]
									</select>
								</td>
							[/#list]
							<td>
								<a href="javascript:;" class="deleteSpecificationProduct">[${message("admin.common.delete")}]</a>
							</td>
						</tr>
						[#if product.specifications?has_content]
							<tr>
								<td>
									${message("admin.product.currentSpecification")}
									<input type="hidden" name="specificationProductIds" value="${product.id}" />
								</td>
								[#list specifications as specification]
									<td class="specification_${specification.id}[#if !product.specifications?seq_contains(specification)] hidden[/#if]">
										<select name="specification_${specification.id}" disabled="disabled">
											[#list specification.specificationValues as specificationValue]
												<option value="${specificationValue.id}"[#if product.specificationValues?seq_contains(specificationValue)] selected="selected"[/#if]>${specificationValue.name}</option>
											[/#list]
										</select>
									</td>
								[/#list]
								<td>
									-
								</td>
							</tr>
						[/#if]
						[#list product.siblings as specificationProduct]
							<tr>
								<td>
									&nbsp;
									<input type="hidden" name="specificationProductIds" value="${specificationProduct.id}" />
								</td>
								[#list specifications as specification]
									<td class="specification_${specification.id}[#if !specificationProduct.specifications?seq_contains(specification)] hidden[/#if]">
										<select name="specification_${specification.id}" disabled="disabled">
											[#list specification.specificationValues as specificationValue]
												<option value="${specificationValue.id}"[#if specificationProduct.specificationValues?seq_contains(specificationValue)] selected="selected"[/#if]>${specificationValue.name}</option>
											[/#list]
										</select>
									</td>
								[/#list]
								<td>
									<a href="javascript:;" class="deleteSpecificationProduct">[${message("admin.common.delete")}]</a>
									<a href="edit.jhtml?id=${specificationProduct.id}">[${message("admin.common.edit")}]</a>
								</td>
							</tr>
						[/#list]
					</table>
				</td>
			</tr>
		</table>
		<table class="input">
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