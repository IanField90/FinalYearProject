class PartsController < ApplicationController
  # GET /parts/1
  def show
    @part = Part.find(params[:id])
    respond_to do |format|
      format.html
      format.json { render :json => @part }
    end
  end

  # GET /parts
  def index
    @parts = Part.all
    respond_to do |format|
      format.html
      format.json { render :json => @parts }
    end
  end
  
  # DELETE /parts/1
  def destroy
    @part = Part.find(params[:id])
    if is_user_admin
      @part.destroy
      redirect_to parts_path
    else
      redirect_to part_path #, :notice => "Cannot delete unless admin"
    end
  end
  
  # GET programmes/1/parts/new
  def new
    @programme = Programme.find(params[:programme_id])
    @part = @programme.parts.new(params[:part])
    if is_user_admin
      render :new
    else
      redirect_to part_path, :notice => "Cannot create unless admin"
    end
  end

  # POST /courses
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

  # PUT /courses/1
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
  
  # GET /parts/1/edit
  def edit
    @part = Part.find(params[:id])
    if !is_user_admin
      redirect_to part_path(@part), :notice => "Cannot edit unless admin"
    end
  end

end
