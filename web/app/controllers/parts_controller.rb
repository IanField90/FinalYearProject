class PartsController < ApplicationController
  def show
    @part = Part.find(params[:id])
  end

  def index
    @parts = Part.all
  end
  
  def destroy
    @part = Part.find(params[:id])
    @part.destroy
    redirect_to parts_path
  end
  
  def new
    @programme = Programme.find(params[:programme_id])
    @part = @programme.parts.new(params[:part])
    if is_user_admin
      render :new
    else
      redirect_to part_path
    end
  end

  def create
    if is_user_admin
      @part = Part.new(params[:part])
      if @part.save
        redirect_to @part, :notice => 'Part was successfully created.'
      else
        render :action => "new"
      end
    else
      redirect_to parts_path
    end
  end

  def update
    if is_user_admin
      #can update
      @part = Part.find(params[:id])
      if @part.update_attributes(params[:part])
        redirect_to @part, :notice => 'Part was successfully updated.'
      else
        render :action => "edit"
      end
    else
      redirect_to parts_path
    end
  end
  
  def edit
    @part = Part.find(params[:id])
  end

end
