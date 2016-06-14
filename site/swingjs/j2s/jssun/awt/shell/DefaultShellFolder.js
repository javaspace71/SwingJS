Clazz.declarePackage ("jssun.awt.shell");
Clazz.load (["jssun.awt.shell.ShellFolder"], "jssun.awt.shell.DefaultShellFolder", ["java.io.File", "$.ObjectStreamException"], function () {
c$ = Clazz.declareType (jssun.awt.shell, "DefaultShellFolder", jssun.awt.shell.ShellFolder);
Clazz.makeConstructor (c$, 
function (parent, f) {
Clazz.superConstructor (this, jssun.awt.shell.DefaultShellFolder, [parent, f.getAbsolutePath ()]);
}, "jssun.awt.shell.ShellFolder,java.io.File");
Clazz.overrideMethod (c$, "writeReplace", 
function () {
return  new java.io.File (this.getPath ());
});
Clazz.defineMethod (c$, "listFiles", 
function () {
var files = Clazz.superCall (this, jssun.awt.shell.DefaultShellFolder, "listFiles", []);
if (files != null) {
for (var i = 0; i < files.length; i++) {
files[i] =  new jssun.awt.shell.DefaultShellFolder (this, files[i]);
}
}return files;
});
Clazz.overrideMethod (c$, "isLink", 
function () {
return false;
});
Clazz.overrideMethod (c$, "isHidden", 
function () {
var fileName = this.getName ();
if (fileName.length > 0) {
return (fileName.charAt (0) == '.');
}return false;
});
Clazz.overrideMethod (c$, "getLinkLocation", 
function () {
return null;
});
Clazz.overrideMethod (c$, "getDisplayName", 
function () {
return this.getName ();
});
Clazz.overrideMethod (c$, "getFolderType", 
function () {
if (this.isDirectory ()) {
return "File Folder";
} else {
return "File";
}});
Clazz.overrideMethod (c$, "getExecutableType", 
function () {
return null;
});
});
