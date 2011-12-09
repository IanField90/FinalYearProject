class CoursesController < ApplicationController

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
  
  def show
    @course = Course.find(params[:id])
    
    # respond_to |format| do
    #       format.html #show.html.erb
    #     end
    render :show
  end
  
  # GET /courses/1/edit
  def edit
    @course = Course.new(params[:course])
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

  def update
    render :update
  end

  def destroy
    #render :destroy
    @course = Course.find(params[:id])
    @course.destroy
    redirect_to courses_path
  end

end
