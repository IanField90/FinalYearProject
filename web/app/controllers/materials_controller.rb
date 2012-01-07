class MaterialsController < ApplicationController
  # GET /parts/1/materials/new
  def create
    if is_user_admin
      @material = Material.new(params[:material])
      if @material.save
        redirect_to @material, :notice => 'Material was successfully created'
      else
        render :action => "new"
      end 
    else
      redirect_to courses_path
    end
    
  end

  # GET /materials
  # GET /parts/1/materials
  def index
    if params[:part_id]
      @materials = Material.find(:all, :conditions => ["part_id = ?", params[:part_id]])
    else
      @materials = Material.all
    end
    respond_to do |format|
      format.html
      format.json { render :json => @materials }
    end
  end

  # DELETE /materials/1
  def destroy
      @material = Material.find(params[:id])
      if is_user_admin
        @material.destroy
      end
      redirect_to part_path(@material.part_id)
  end

  # GET /materials/1/edit
  def edit
    @material = Material.find(params[:id])
    if !is_user_admin
      redirect_to part_path(@material.part_id)
    end
  end

  # GET /parts/1/materials/new
  def new
    @part = Part.find(params[:part_id])
    @material = Material.new(params[:material])
    if is_user_admin
      render "new"
    else
      redirect_to part_path(@part)
    end
  end

  # GET /materials/1
  def show
    @material = Material.find(params[:id])
  end

  def update
    @material = Material.find(params[:id])
    
    if is_user_admin
      #can update      
      if @material.update_attributes(params[:material])
        redirect_to @material, :notice => 'Material was successfully updated.'
      else
        render :action => "edit"
      end
    else
      redirect_to part_path(@material.part_id)
    end
  end

end
