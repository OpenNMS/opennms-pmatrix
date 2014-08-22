/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2010-2012 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2012 The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OpenNMS(R).  If not, see:
 *      http://www.gnu.org/licenses/
 *
 * For more information contact:
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 *******************************************************************************/

package org.opennms.features.vaadin.pmatrix.ui;

import java.util.Collection;

import org.opennms.features.vaadin.pmatrix.engine.PmatrixDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class PmatrixTable extends CustomComponent {
	@AutoGenerated
	private HorizontalLayout mainLayout;

	@AutoGenerated
	private VerticalLayout verticalLayout_1;

	@AutoGenerated
	private Label label_2;

	@AutoGenerated
	private Table pmatrixTable;

	@AutoGenerated
	private Label label_1;

	private static final Logger LOG = LoggerFactory.getLogger(PmatrixTable.class);
			
	/*- VaadinEditorProperties={"grid":"RegularGrid,93","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	private PmatrixDataSource pmatrixDataSource =null;
	
	private boolean hidePmatrixHeaders=false;
	

	public PmatrixDataSource getPmatrixDataSource() {
		return pmatrixDataSource;
	}


	public void setPmatrixDataSource(PmatrixDataSource pmatrixDataSource) {
		this.pmatrixDataSource = pmatrixDataSource;
		pmatrixDataSource.setAttachedComponent(this); // passes synchronization reference back
	}
	
	// used to release resources when UI is detached
	private void releaseResources() {
        LOG.debug("PmatrixTable is detached from UI. Cleaning up resources");
        try {
			if(pmatrixDataSource!=null){
				pmatrixDataSource.unRegisterWithDataPointMapDao();
				pmatrixDataSource.setAttachedComponent(null);
				pmatrixDataSource=null; // release reference to data source
			}
		} catch (Exception e) {
	        LOG.error("Problem cleaning up PmatrixTable resources:",e);
		}
    }

	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor. This constructor ensures the class must have a pmatrixDataSource defined first
	 * 
	 * * @param pmatrixDataSource data source used to drive the pmatrix table
	 * this is injected by Spring
	 */
	public PmatrixTable(PmatrixDataSource pmatrixDataSource){
		pmatrixDataSource.setAttachedComponent(this); // passes synchronization reference back
		
		// detach listener is used to remove resources when the table is detached from the UI
		// specifically it unregisters the pmatrixDataSource
        this.addDetachListener(new DetachListener() {
			public void detach(DetachEvent event) {
				releaseResources();
            }            
        });
		
		buildMainLayout();
		setCompositionRoot(mainLayout);
		
		// TODO add user code here
		this.pmatrixDataSource=pmatrixDataSource;
		
		// set the name field
		String pmatrixTitle = (pmatrixDataSource.getPmatrixTitle()== null) ? "Set Pmatrix Name" : pmatrixDataSource.getPmatrixTitle();
		
		label_1.setContentMode(ContentMode.HTML);
		label_1.setValue("<div style=\"color:black; \" />"+pmatrixTitle+"</div>");
		
		//set the description field
		String pmatrixDescription = (pmatrixDataSource.getPmatrixDescription()== null) ? "Set Pmatrix Description" : pmatrixDataSource.getPmatrixDescription();
		label_2.setContentMode(ContentMode.HTML);
		label_2.setValue("<div style=\"white-space: normal;\" />"+pmatrixDescription+"</div>");
		
		// set up the theme for the container
		pmatrixTable.addStyleName(".pmatrixtheme .v-table-caption-container");
		
		pmatrixTable.setContainerDataSource(getPmatrixDataSource().getDataSourceContainer());
		
		//set table height and width from datasource
		//if no height and width set then use defaults set here
		if(getPmatrixDataSource().getComponentHeight()!=null){
			pmatrixTable.setHeight(getPmatrixDataSource().getComponentHeight());
		}
		if(getPmatrixDataSource().getComponentWidth()!=null){
			pmatrixTable.setWidth(getPmatrixDataSource().getComponentWidth());
		}
		
		// set up table to resize automatically (does this work?)
		for(Object  pid: pmatrixTable.getContainerPropertyIds()){
			pmatrixTable.setColumnAlignment(pid, Align.CENTER);
			pmatrixTable.setColumnWidth(pid, -1);
		};
		


		// hide column and row headers if hidePmatrixHeaders=true
		hidePmatrixHeaders = pmatrixDataSource.getHidePmatrixHeaders();
		if(hidePmatrixHeaders){
			// hide column headers
			pmatrixTable.setColumnHeaderMode(Table.ColumnHeaderMode.HIDDEN);
			
			// hide first row (row headers)
			pmatrixTable.setColumnCollapsingAllowed(true);
			pmatrixTable.setColumnCollapsed("rowName", true);
			// make all columns non collapsible except first column "rowName"
			for(Object  pid: pmatrixTable.getContainerPropertyIds()){
				if(! pid.equals("rowName")) pmatrixTable.setColumnCollapsible(pid, false);
			};
		}
		
		pmatrixTable.setColumnHeader("rowName", ""); // remove rowname from the top left column name
		
		pmatrixTable.setImmediate(true);
		pmatrixTable.setVisible(true);
		
	}

	@AutoGenerated
	private HorizontalLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new HorizontalLayout();
		mainLayout.setImmediate(true);
		mainLayout.setWidth("-1px");
		mainLayout.setHeight("-1px");
		mainLayout.setMargin(false);
		
		// top-level component properties
		setWidth("-1px");
		setHeight("-1px");
		
		// verticalLayout_1
		verticalLayout_1 = buildVerticalLayout_1();
		mainLayout.addComponent(verticalLayout_1);
		
		return mainLayout;
	}


	@AutoGenerated
	private VerticalLayout buildVerticalLayout_1() {
		// common part: create layout
		verticalLayout_1 = new VerticalLayout();
		verticalLayout_1.setImmediate(false);
		verticalLayout_1.setWidth("-1px");
		verticalLayout_1.setHeight("-1px");
		verticalLayout_1.setMargin(true);
		
		// label_1
		label_1 = new Label();
		label_1.setImmediate(true);
		label_1.setWidth("-1px");
		label_1.setHeight("-1px");
		label_1.setValue("Label");
		verticalLayout_1.addComponent(label_1);
		verticalLayout_1.setComponentAlignment(label_1, new Alignment(20));
		
		// pmatrixTable
		pmatrixTable = new Table();
		pmatrixTable.setImmediate(true);
		pmatrixTable.setWidth("2000px");
		pmatrixTable.setHeight("900px");
		verticalLayout_1.addComponent(pmatrixTable);
		
		// label_2
		label_2 = new Label();
		label_2.setImmediate(false);
		label_2.setWidth("-1px");
		label_2.setHeight("-1px");
		label_2.setValue("Label");
		verticalLayout_1.addComponent(label_2);
		
		return verticalLayout_1;
	}

}
