package org.flabs.bucket

class Bucket extends Expando {
    String identifier

    @Override
    def void setProperty(String property, Object newValue) {
         super.setProperty(property, new Petal(payload: newValue))
    }

    @Override
    Object getProperty(String property) {
        if(['identifier'].contains(property)) {
            return this.identifier
        }
        def existing = providesField(property)
        if(existing != null) {
            return existing
        }
        super.getProperty(property)
    }



    private def providesField(String property) {
        def existing = getProperties().get(property)
        if(existing != null) {
            return existing.payload
        }
        return null
    }

}
