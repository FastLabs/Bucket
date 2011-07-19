package org.flabs.bucket

class Bucket extends Expando {
    String identifier

    @Override
    def void setProperty(String property, Object newValue) {
         super.setProperty(new Petal(payload: newValue))
    }

}
