package com.zenza.pets.ipc.utils.exceptions

class MissingUserException(field: String, value: String)
    : RuntimeException("Could not find user with $field $value in our datastore")