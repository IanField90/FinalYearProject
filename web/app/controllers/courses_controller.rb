class CoursesController < ApplicationController
  # GET /courses
  def index
    @courses = Course.all
  end

  # GET /courses/new
  def new
    # current_user is nill if no-one is logged in, check for that, then if they are admin
    @course = Course.new
    if is_user_admin
      render :new
    else
      redirect_to courses_path
    end
  end
  
  # GET /courses/1
  def show
    @course = Course.find(params[:id])
    #@programmes = @course.programmes
    #@programmes = Programme.find(:course_id => params[:id])
    # respond_to |format| do
    #       format.html #show.html.erb
    #     end
    #render :show
  end
  
  # GET /courses/1/edit
  def edit
    @course = Course.find(params[:id])
    if !is_user_admin
      redirect_to courses_path
    end
  end
  
  # POST /courses
  def create
    if is_user_admin
      @course = Course.new(params[:course])
      
      if @course.save
        redirect_to @course, :notice => 'Course was successfully created.'
      else
        render :action => "new"
      end
    else
      redirect_to courses_path
    end
  end

  # PUT /courses/1
  def update
    @course = Course.find(params[:id])
    if @course.update_attributes(params[:course])
      redirect_to @course, :notice => 'Course was successfully updated.'
    else
      render :action => "edit"
    end
    #render :update
  end

  # DELETE /courses/1
  def destroy
    #render :destroy
    @course = Course.find(params[:id])
    @course.destroy
    redirect_to courses_path
  end

end
