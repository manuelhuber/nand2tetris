package compiler

abstract class JackCode {
//    fun JackDSL.compile(): T {
//            return inTag(javaClass.simpleName.decapitalize()) {
//                compileThis()
//            }
//        }
//
//        abstract fun JackDSL.compileThis(): T;
//    }
}

fun tag(tag: String): (String) -> String {
    return { content: String -> "<$tag> $content </$tag>" }
}
