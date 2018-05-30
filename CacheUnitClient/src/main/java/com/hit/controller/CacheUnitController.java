package com.hit.controller;

import java.util.Observable;
import java.util.Observer;

import com.hit.model.Model;
import com.hit.view.View;

public class CacheUnitController implements Controller{
	Model myModel;
	View myView;
	public CacheUnitController(Model model, View view) {
		myModel = model;
		myView = view;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
