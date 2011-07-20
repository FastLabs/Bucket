package org.flabs.bucket.context

import org.flabs.bucket.Bucket

class MonitorContext {
    static def providesField(Bucket bucket, String property) {
          bucket.getProperties().get(property)
    }
}
