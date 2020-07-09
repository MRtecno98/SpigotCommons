package com.spigot.libraries.permissions;

import java.util.Arrays;
import java.util.regex.Pattern;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Permission {
	private @Setter(AccessLevel.PROTECTED) Permission previousNode;
    private String nodeValue;
    
    public Permission(Permission prev, String perm) {
        nodeConstructor(prev, perm);
    }
    
    public Permission(String permission) {
        stringConstructor(permission);
    }
    
    private void stringConstructor(String permission) {
        if(!permission.contains(Pattern.quote("."))) this.nodeValue = permission;
        else {
            String[] splitted = permission.split(Pattern.quote("."));
            nodeConstructor(new Permission(splitted[0]), String.join(".", Arrays.copyOfRange(splitted, 1, splitted.length)));
        }
    }
    
    private void nodeConstructor(Permission prev, String perm) {
        stringConstructor(perm);
        getFirstNode().setPreviousNode(prev);
    }
    
    public Permission getFirstNode() {
        return getPreviousNode() != null ? getPreviousNode().getFirstNode() : this;
    }
    
    @Override
    public String toString() {
        return (previousNode != null ? previousNode.toString() + "." : "") + getNodeValue();
    }
}
