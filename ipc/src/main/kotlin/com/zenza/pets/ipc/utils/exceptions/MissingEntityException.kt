package com.zenza.pets.ipc.utils.exceptions

class MissingEntityException(entity: String, field: String, value: String)
    : RuntimeException("Could not find $entity with $field $value in our datastore")