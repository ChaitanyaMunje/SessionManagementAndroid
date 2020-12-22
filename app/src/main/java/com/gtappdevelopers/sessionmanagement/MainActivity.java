package com.gtappdevelopers.sessionmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import de.blox.treeview.BaseTreeAdapter;
import de.blox.treeview.TreeNode;
import de.blox.treeview.TreeView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //creating a variable for tree view.
        TreeView treeView = findViewById(R.id.idTreeView);
        //creating adapter class for our treeview using basetree adapter.
        //inside base tree adapter you have to pass viewholder class along with context and your layout file for treeview node.
        BaseTreeAdapter<Viewholder> adapter = new BaseTreeAdapter<Viewholder>(this, R.layout.tree_view_node) {
            @NonNull
            @Override
            public Viewholder onCreateViewHolder(View view) {
                return new Viewholder(view);
            }

            @Override
            public void onBindViewHolder(Viewholder viewHolder, Object data, int position) {
                //inside our on bind view holder method we are setting data from object to text view.
                viewHolder.textView.setText(data.toString());

            }
        };
        //below line is setting adapter for our tree.
        treeView.setAdapter(adapter);
        //below tree node is a parent node of our tree node which is Geeks for Geeks.
        TreeNode root = new TreeNode("Geeks for Geeks");
        //below node is the first child node of our root node ie Geeks for Geeks.
        TreeNode DSAchildNode = new TreeNode("DSA");
        //below node is the second child of our root node ie Geeks for Geeks.
        TreeNode AlgoChildNode = new TreeNode("Algorithm");
        //below node is the third child of our root node ie Geeks for Geeks.
        TreeNode languageNode = new TreeNode("Language");
        //below node is the first child of our language node.
        TreeNode CchildNode = new TreeNode("C++");
        //below node is the second child of our language node.
        TreeNode javaChildNode = new TreeNode("Java");
        //below node is the first child of our DSA node.
        TreeNode arrayChild = new TreeNode("Arrays");
        //below node is the second child of our DSA node.
        TreeNode stringChild = new TreeNode("Strings");
        //below node is the first child of our Algorithm node.
        TreeNode sortingChildNode = new TreeNode("Sorting");

        //below lines is used for adding child nodes to our root nodes.
        root.addChild(DSAchildNode);
        root.addChild(languageNode);
        root.addChild(AlgoChildNode);
        //below lines is used to add languages to our Language node. we are adding c++,java to our language node.
        languageNode.addChild(CchildNode);
        languageNode.addChild(javaChildNode);
        //below line isused to add arrays, strings to our dsa node. we are adding Arrays,Strings to our DSA node.
        DSAchildNode.addChild(arrayChild);
        DSAchildNode.addChild(stringChild);
        //below line is used for adding sorting algo to our Algorithm node.
        AlgoChildNode.addChild(sortingChildNode);

        //below line is for setting our root node. Inside our root node we are passing "root" as our root node.
        adapter.setRootNode(root);


    }

}