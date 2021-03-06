class ProgrammesController < ApplicationController
  # GET /programmes/1
  def show
    @programme = Programme.find(params[:id])
    respond_to do |format|
      format.html
      format.json { render :json => @programme }
    end
  end
  
  # GET /programmes/new
  def new
    # current_user is nill if no-one is logged in, check for that, then if they are admin
    @course = Course.find(params[:course_id])
    @programme = @course.programmes.new(params[:programme]) # stop using create and use new, but retain params via hidden field
    if is_user_admin
      render :new
    else
      redirect_to programme_path, :notice => "Cannot create unless admin"
    end
  end
  
  # GET /programmes
  def index
    @programmes = Programme.all
    respond_to do |format|
      format.html
      format.json { render :json => @programmes }
    end
  end

  # DELETE /programmes/1
  def destroy
    @programme = Programme.find(params[:id])
    if is_user_admin
      @programme.destroy
    else
      redirect_to programme_path(@programme), :notice => "Cannot delete unless admin"
    end
  end

  # POST /programmes
  def create
    if is_user_admin
      @programme = Programme.new(params[:programme])
      if @programme.save
        redirect_to @programme, :notice => 'Programme was successfully created.'
      else
        render :action => "new"
      end
    else
      redirect_to programmes_path
    end
  end
  
  # PUT /programmes/1
  def update
    if is_user_admin
      #can update
      @programme = Programme.find(params[:id])
      if @programme.update_attributes(params[:programme])
        redirect_to @programme, :notice => 'Programme was successfully updated.'
      else
        render :action => "edit"
      end
    else
      redirect_to programmes_path
    end
  end
  
  # GET /programmes/1/edit
  def edit
    @programme = Programme.find(params[:id])
    if !is_user_admin
      redirect_to programme_path(@programme), :notice => "Cannot edit unless admin"
    end
  end

end
