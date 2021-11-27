package com.springcloud.inventory

import org.apache.commons.lang3.StringUtils

data class OrderWithUrl(
  var order: Order?,
  var url: String?
) {
  constructor() : this(null, StringUtils.EMPTY)
}

data class Address(
  var line1: String?,
  var city: String?,
  var state: String?
) {
  constructor() : this(StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY)
}

data class Item(
  var qty: Int?,
  var unitPrice: Double?,
  var cost: Double?
) {
  constructor() : this(0, 0.0, 0.0)
}

data class Cart(
  var creditCard: String?,
  var legalName: String?,
  var address: Address? = null,
  var items: List<Item> = emptyList()
) {
  constructor() : this(StringUtils.EMPTY, StringUtils.EMPTY)
}

data class Order(
  var orderId: String?,
  var creditCard: String?,
  var legalName: String?,
  var address: Address? = null,
  var items: List<Item> = emptyList(),
  var totalCost: Double? = 0.0,
  var inventoryRef: String?,
  var paymentRef: String?,
  var shippingRef: String?
) {
  constructor() : this(
    StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY,
    null, emptyList<Item>(), 0.0,
    StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY
  )

  constructor(cart: Cart) : this() {
    this.creditCard = cart.creditCard
    this.legalName = cart.legalName
    this.address = cart.address
    this.items = cart.items
  }
}
