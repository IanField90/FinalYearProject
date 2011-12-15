class MaterialsController < ApplicationController
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

  def destroy
      @material = Material.find(params[:id])
      @id = @material.part_id
      if is_user_admin
        @material.destroy
      end
      redirect_to part_path(@id)
  end

  def edit
    @material = Material.find(params[:id])
    if !is_user_admin
      redirect_to part_path(@material.part_id)
    end
  end

  def new
    @part = Part.find(params[:part_id])
    @material = Material.new(params[:material])
    if is_user_admin
      render "new"
    else
      redirect_to part_path(@part)
    end
  end

  def show
    @material = Material.find(params[:id])
  end

  def update
    if is_user_admin
      #can update
      @material = Material.find(params[:id])
      @id = @material.part_id
      
      if @material.update_attributes(params[:material])
        redirect_to @material, :notice => 'Material was successfully updated.'
      else
        render :action => "edit"
      end
    else
      redirect_to part_path(@id)
    end
  end

end
